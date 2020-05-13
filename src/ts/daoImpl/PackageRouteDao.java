package ts.daoImpl;

import java.util.List;

import ts.daoBase.BaseDao;
import ts.model.PackageRoute;
import ts.model.TransPackage;

public class PackageRouteDao extends BaseDao<PackageRoute, Integer> {

	public PackageRouteDao() {
		super(PackageRoute.class);
	}
	
	//lyy ÐÂÔö
	public List<PackageRoute> getPackageRouteListByPkg(TransPackage transPackage) {
		return findBy("pkg", transPackage, "SN", true);
	}
	
}
