package ts.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.TransPackage;

public class TransPackageDao extends BaseDao<TransPackage,String> {
	public TransPackageDao(){
		super(TransPackage.class);
	}
	public List<TransPackage> findbyExpressSheetId(String ExpressSheetId) {
		/*
		String sql = "{alias}.ID in (select ExpressID from TransPackageContent where Status = 0 and PackageID = '"+pkg_id+"')";
		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
		list = findBy("ID", true, Restrictions.sqlRestriction(sql));		
		return list;
		 */
		String sql = "{alias}.ID in (select PackageID from TransPackageContent where  ExpressID = \'"+ExpressSheetId+ "\')";
		List<TransPackage> list = new ArrayList<TransPackage>();
		list = findBy("ID", true, Restrictions.sqlRestriction(sql));
		System.out.println(sql);
		System.out.println(list);
		return list;
	}
	
	public List<TransPackage> findbyExpressSheetIdList(String ExpressSheetId) {
		/*
		String sql = "{alias}.ID in (select ExpressID from TransPackageContent where Status = 0 and PackageID = '"+pkg_id+"')";
		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
		list = findBy("ID", true, Restrictions.sqlRestriction(sql));		
		return list;
		 */
		String sql = "{alias}.ID in (select PackageID from TransPackageContent where  ExpressID = \'"+ExpressSheetId+ "\')";
		List<TransPackage> list = new ArrayList<TransPackage>();
		list = findBy("ID", true, Restrictions.sqlRestriction(sql));
		System.out.println(sql);
		System.out.println(list);
		return list;
	}
}
