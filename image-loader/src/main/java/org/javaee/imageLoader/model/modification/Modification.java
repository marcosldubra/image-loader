package org.javaee.imageLoader.model.modification;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Immutable;
import org.javaee.imageLoader.model.uploadedImage.UploadedImage;

@Entity
@Immutable
@Table(name="Modification")
@BatchSize(size = 10)
public class Modification {
	
	public enum ModificationType { NAME, SOURCE};
	
	private long modificationId;
	private String description;
	private Calendar modificationDate;
	private ModificationType modificationType;
	private UploadedImage image;
	
	public Modification() {
		
	}
	
	public Modification(String description, Calendar modificationDate,
			ModificationType modificationType, UploadedImage image) {
		this.description = description;
		this.modificationDate = modificationDate;
		this.modificationType = modificationType;
		this.image = image;
	}

	@SequenceGenerator(name = "modificationIdGenerator",
			sequenceName = "modificationIdSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "modificationIdGenerator")
	public long getModificationId() {
		return modificationId;
	}

	public void setModificationId(long modificationId) {
		this.modificationId = modificationId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Calendar modificationDate) {
		this.modificationDate = modificationDate;
	}

	public ModificationType getModificationType() {
		return modificationType;
	}

	public void setModificationType(ModificationType modificationType) {
		this.modificationType = modificationType;
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "uploadedImageId")
	public UploadedImage getImage() {
		return image;
	}

	public void setImage(UploadedImage image) {
		this.image = image;
	}
	
	
}
