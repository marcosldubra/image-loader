CREATE database IF NOT EXISTS imageLoader;

USE imageLoader;

DROP TABLE IF EXISTS UploadedImage;
DROP TABLE IF EXISTS Modification;

CREATE TABLE UploadedImage (
	uploadedImageId BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	imageName VARCHAR(100) NOT NULL,
	source VARCHAR(150) NOT NULL,
	version BIGINT NOT NULL)
ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE Modification (
	modificationId BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	modificationType TINYINT NOT NULL,
	description VARCHAR (400) NOT NULL,
	modificationDate DATE,
	uploadedImageId BIGINT NOT NULL,
	CONSTRAINT fkModificationUploadedImage FOREIGN KEY (uploadedImageId)
		REFERENCES UploadedImage(uploadedImageId)
		ON DELETE CASCADE
		ON UPDATE NO ACTION)
ENGINE = InnoDB DEFAULT CHARSET=utf8;

--CREATE USER 'imageLoader'@'localhost' IDENTIFIED BY '12345';
--GRANT ALL PRIVILEGES ON imageLoader.* TO 'imageLoader'@'localhost';
