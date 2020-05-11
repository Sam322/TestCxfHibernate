package ts.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.ExpressSheet;
import ts.model.TransPackage;
import ts.model.TransPackageContent;

public class TransPackageContentDao extends BaseDao<TransPackageContent,Integer> {
	public TransPackageContentDao(){
		super(TransPackageContent.class);
	}
	
	public TransPackageContent get(String expressId, String packageId){
		List<TransPackageContent> list  = new ArrayList<TransPackageContent>();
		list = super.findBy("SN", true, 
				Restrictions.sqlRestriction("ExpressID = '"+ expressId + "' and PackageID = '" + packageId +"'"));
		if(list.size() == 0)
			return null;
		return list.get(0);
	}
	//需要查找包裹里的什么状态的快件列表
	public List<TransPackageContent> getTransPackageContents(TransPackage transPackage,int status) {
		List<TransPackageContent> list = null;
		list = findBy("pkg",transPackage,"SN",true);
		for(TransPackageContent pc:list) {
			System.out.println("执行了这个方法！transpackageDao"+pc.toString());
			ExpressSheet expressSheet = pc.getExpress();
			if(expressSheet.getStatus() != status) {
				list.remove(pc);
			}
		}
		return list;
		
	}
	public int getSn(String expressId, String packageId){
		TransPackageContent cn = get(expressId,packageId);
		if(cn == null){
			return 0;
		}
		return get(expressId,packageId).getSN();
	}

	public void delete(String expressId, String packageId){
		List<TransPackageContent> list  = new ArrayList<TransPackageContent>();
		list = super.findBy("SN", true, 
				Restrictions.eq("ExpressID", expressId),
				Restrictions.eq("PackageID",packageId));
		for(TransPackageContent pc : list)
			super.remove(pc);
		return ;
	}
}
