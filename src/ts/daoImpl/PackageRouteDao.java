package ts.daoImpl;

import ts.daoBase.BaseDao;

import ts.model.PackageRoute;

public class PackageRouteDao extends BaseDao<PackageRoute, Integer> {

	public PackageRouteDao() {
		super(PackageRoute.class);
	}
}
