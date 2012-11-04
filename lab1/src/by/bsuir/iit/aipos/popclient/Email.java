package by.bsuir.iit.aipos.popclient;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;


/** �����, �������������� ���������� �� ������� ������
 *
 */
public class Email {

	private String mFieldSubject = null;
	private String mFieldFrom = null;
	private String mFieldDate = null;
	private String mFieldMessage = "";
	private ArrayList<Image> mReceivedImages = new ArrayList<Image>();

	/** ������ ������ �� ���������� � mime �����
	 * @param source ������ � �������� ����
	 */
	public Email(String source) {
		if(source.startsWith("+OK"))
			parseAll(source);
	}

	/** �������� ���� ���������
	 * @return ���� ���������
	 */
	public final String getFieldSubject() {
		return mFieldSubject;
	}

	/** �������� ����� �����������
	 * @return ����� �����������
	 */
	public final String getFieldFrom() {
		return mFieldFrom;
	}

	/** �������� ���� �����������
	 * @return ���� �����������
	 */
	public final String getFieldDate() {
		return mFieldDate;
	}

	/** �������� ����� ���������
	 * @return ����� ���������
	 */
	public final String getFieldMessage() {
		return mFieldMessage;
	}

	/** �������� �������� �����������
	 * @return �����������
	 */
	public List<Image> getReceivedImages() {
		return mReceivedImages;
	}

	/** ��������� ����������� �� ����
	 * @param folderName ����� ����������
	 * @throws Exception ���� ��������� ������ ��� ������ ������ �� ����
	 */
	public void saveImage(String folderName) throws Exception {
		try {
			if(mReceivedImages != null) {
				for (int i = 0; i < mReceivedImages.size(); i++) {
					File file = new File(folderName + File.pathSeparator + "image" + i + ".png");
		            if (!file.exists()) {
						file.createNewFile();
		            }
		            ImageIO.write((RenderedImage) mReceivedImages.get(i), "png", file);
				}
			}
		} catch (IOException e) {
			throw new Exception("������ ��� ������ ������ �� ����");
		}
	}

	/** ��������� ��������� �� ����
	 * @param folderName ����� ����������
	 * @throws Exception ���� ��������� ������ ��� ������ ������ �� ����
	 */
	public void saveMail(String folderName) throws Exception {
		try {
			File file = new File(folderName + "/mail" + ".txt");
			if (!file.exists()) {
				file.createNewFile();
	        }
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.append("��: " + mFieldFrom + "\r\n");
			fileWriter.append("����: " + mFieldDate + "\r\n");
			fileWriter.append("����: " + mFieldSubject + "\r\n");
			fileWriter.append("���������: " + mFieldMessage + "\r\n");
			fileWriter.flush();
			fileWriter.close();

		} catch (IOException e) {
			throw new Exception("������ ��� ������ ������ �� ����");
		}
	}

	/** ������ ������
	 * @param source �������� ���������
	 */
	private void parseAll(final String source) {
		mFieldDate = parseField(source, "Date");
		mFieldSubject = parseField(source, "Subject");
		mFieldFrom = parseField(source, "From");
		parseMessage(source);
	}

	/** �������� �������� ���������
	 * @param source �������� �����
	 * @param fieldName �������� ���������
	 * @return �������� ���������
	 */
	private static String parseField(String source, String fieldName) {
		int fromIndex = source.indexOf(fieldName + ": ");
		int fromIndexEnd = source.indexOf("\n", fromIndex);
		String field = source.substring(fromIndex + fieldName.length() + 2, fromIndexEnd).trim();

		return getDecodedText(field);
	}

	/** ������ ���������
	 * @param source �������� ���������
	 */
	private void parseMessage(String source) {
		int contentTypeIndex = source.indexOf("Content-Type: ");
		
		// ���� source ��������� multipart
		if(source.substring(contentTypeIndex + 14).startsWith("multipart")) {
			// boundary - ����� ������ ���������
			int boundaryIndex = source.indexOf("boundary=", contentTypeIndex);
			String boundary = null;
			int boundaryEndIndex = 0;
			
			// �������� ����� ���� ��������� � ������� � ����� � ���, ������������ ��� ��������
			if(source.charAt(boundaryIndex + 9) == '"') {
				boundaryEndIndex = source.indexOf('"', boundaryIndex + 10);
				boundary = source.substring(boundaryIndex + 10, boundaryEndIndex);
			} else {
				boundaryEndIndex = source.indexOf("\n", boundaryIndex + 10);
				boundary = source.substring(boundaryIndex + 9, boundaryEndIndex);
			}

			// ���������� ������ � ����� ����� ����������� multipart ���������
			int contentBegin = source.indexOf("--" + boundary);
			int contentEnd = source.indexOf("--" + boundary + "--");

			int contentBeginIndex = contentBegin;

			// ������������ ������ ����� multipart ���������
			while(contentBeginIndex < contentEnd) {
				int contentEndIndex = source.indexOf("--" + boundary, contentBeginIndex + 2);
				parseContent(source.substring(contentBeginIndex + ("--" + boundary).length() + 1, contentEndIndex));
				contentBeginIndex = contentEndIndex;
			}
		}
	}

	/** ������ ����������� ���������
	 * @param content ���������� � �������� ����
	 */
	private void parseContent(String content) {
		int contentTypeIndex = content.indexOf("Content-Type: ");
		// ���������� ��� ������ �����: multipart, text ��� image
		if(content.substring(contentTypeIndex + 14).startsWith("multipart")) {
			parseMessage(content);
		} else if(content.substring(contentTypeIndex + 14).startsWith("text/plain")) {
			int charsetIndex = content.indexOf("charset=");
			int charsetEndIndex = content.indexOf("\n", charsetIndex);
			String charset = content.substring(charsetIndex + 8, charsetEndIndex);
			if(content.indexOf("Content-Transfer-Encoding: base64") >= 0) {
				int base64ContentBegin = content.indexOf("\n\n") + 2;
				String base64Content = content.substring(base64ContentBegin).replace("\n", "");
				mFieldMessage += fromBase64(base64Content, charset) + "\n";
			} else {
				int contentBegin = content.indexOf("\n\n") + 2;
				mFieldMessage += content.substring(contentBegin) + "\n";
			}
		} else if(content.substring(contentTypeIndex + 14).startsWith("image/png")) {
			if(content.indexOf("Content-Transfer-Encoding: base64") >= 0) {
				int base64ContentBegin = content.indexOf("\n\n") + 2;
				String base64Content = content.substring(base64ContentBegin).replace("\n", "");
				mReceivedImages.add(base64ToImage(base64Content));
			}
		}
	}

	/** ������������� base64 ��� � �����������
	 * @param base64String �������� base64 ���
	 * @return �����������
	 */
	private Image base64ToImage(String base64String)
	{
		byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64String);
		Image image = null;
		try {
			image = ImageIO.read(new ByteArrayInputStream(imageBytes));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}

	/** ������������� ��� ��������� � base64 ������ ���������� � ������� "=?" + ��������� + "?B?" + base64 ����� + "?="
	 * @param source ����, ���������� base64 ������
	 * @return ��������������� �����
	 */
	private static String getDecodedText(String source) {
		// ������ �������������� ������
		int fieldBeginIndex = source.indexOf("=?");

		while(fieldBeginIndex >= 0) {
			// ����������� ����� ��������� ��������� � �������������� �������
			int encodingEndIndex = source.indexOf("?B?");
			// ����� �������������� ������
			int fieldEndIndex = source.indexOf("?=");

			if(fieldBeginIndex < 0 || encodingEndIndex < 0 || fieldEndIndex < 0)
				return null;

			String encoding = source.substring(fieldBeginIndex + 2, encodingEndIndex);
			String base64 = source.substring(encodingEndIndex + 3, fieldEndIndex);

			source = source.replaceFirst("=\\?(.+?)\\?=", fromBase64(base64, encoding));
			fieldBeginIndex = source.indexOf("=?", fieldEndIndex + 2);
		}
		return source;
	}

	/** ������������� base64 ������
	 * @param base64Source �������������� ������
	 * @param encoding ���������
	 * @return ��������������� ������
	 */
	private static String fromBase64(String base64Source, String encoding) {
		String result = null;
		try {
			byte[] decodedBytes = DatatypeConverter.parseBase64Binary(base64Source);
			result = new String(decodedBytes, encoding);
		} catch (UnsupportedEncodingException e) {
			
		}
		return result;
	}


}
