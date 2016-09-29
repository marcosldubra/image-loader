package org.javaee.imageLoader.model.uploadedImage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name="uploadedImage")
@BatchSize(size = 10)
public class UploadedImage {

	private long uploadedImageId;
	private String imageName;
	private String source;
	
	private long version;
	
	public UploadedImage() {
		
	}
	
	public UploadedImage(String imageName, String source) {
		this.imageName = imageName;
		this.source = source;
	}
	
	@SequenceGenerator(name = "imageIdGenerator", sequenceName = "imageIdSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "imageIdGenerator")
	public long getUploadedImageId() {
		return uploadedImageId;
	}
	
	public void setUploadedImageId(long imageId) {
		this.uploadedImageId = imageId;
	}

	@Column(name = "imageName")
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Column(name = "source")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Version
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
	
	
}
