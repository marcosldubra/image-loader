package org.javaee.imageLoader.model.uploadedImage;

import java.util.List;

import org.javaee.modelUtil.dao.GenericDaoHibernate;
import org.javaee.modelUtil.exceptions.InstanceNotFoundException;
import org.springframework.stereotype.Repository;

@Repository("imageDao")
public class UploadedImageDaoHibernate extends GenericDaoHibernate<UploadedImage, Long> implements UploadedImageDao {

	public UploadedImage getImageByName(String imageName) throws InstanceNotFoundException {
		UploadedImage image = (UploadedImage) getSession().createQuery("SELECT u FROM UploadedImage u " +
				"WHERE u.imageName = :imageName").
				setParameter("imageName", imageName).
				uniqueResult();
		
		if (image == null) {
			throw new InstanceNotFoundException (UploadedImage.class.getName(), imageName);
		} else {
			return image;
		}
	}

	@SuppressWarnings("unchecked")
	public List<UploadedImage> findImagesByName(String name, int startIndex, int count) {
		String modifiedName = "%" + name + "%";
		
		List <UploadedImage> images = getSession().createQuery("SELECT u FROM UploadedImage u " +
				"WHERE u.imageName LIKE :modifiedName ORDER BY u.imageName").
				setParameter("modifiedName", modifiedName).
				setFirstResult(startIndex).
				setMaxResults(count).
				list();
		
		return images;
	}

	public int getNumberOfFindImagesByName(String name) {
		String modifiedName = "%" + name + "%";
		
		long numberOf = (Long) getSession().createQuery("SELECT COUNT (u) FROM UploadedImage i " +
				"WHERE u.imageName LIKE :modifiedName ORDER BY u.imageName").
				setParameter("modifiedName", modifiedName).
				uniqueResult();
		
		return (int) numberOf;
	}

	@SuppressWarnings("unchecked")
	public List<UploadedImage> getAllImages() {
		List <UploadedImage> images = getSession().createQuery("SELECT u FROM UploadedImage u " +
				"ORDER BY u.imageName").
				list();
		return images;
	}

}
