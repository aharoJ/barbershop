package io.aharoj.barbershop_backend.modules.image.dto.response;

public class ImageResponse {

  private Long imageId;
  private String fileName;
  private String fileUrl;
  private String fileType;
  private long fileSize;

  public ImageResponse() {
  }

  public ImageResponse(Long imageId, String fileName, String fileUrl, String fileType, long fileSize) {
    this.imageId = imageId;
    this.fileName = fileName;
    this.fileUrl = fileUrl;
    this.fileType = fileType;
    this.fileSize = fileSize;
  }

  public Long getImageId() {
    return imageId;
  }

  public void setImageId(Long imageId) {
    this.imageId = imageId;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public long getFileSize() {
    return fileSize;
  }

  public void setFileSize(long fileSize) {
    this.fileSize = fileSize;
  }

}
