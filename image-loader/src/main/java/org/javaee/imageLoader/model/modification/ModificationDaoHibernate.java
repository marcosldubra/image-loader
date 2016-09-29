package org.javaee.imageLoader.model.modification;

import org.javaee.modelUtil.dao.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

@Repository("modificationDao")
public class ModificationDaoHibernate extends GenericDaoHibernate<Modification, Long> implements ModificationDao {

}
