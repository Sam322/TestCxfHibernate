package ts.daoImpl;

import java.util.List;

import org.springframework.util.Assert;

import ts.daoBase.BaseDao;
import ts.model.TransHistory;
import ts.model.TransPackage;

public class TransHistoryDao extends BaseDao<TransHistory,Integer> {
	public TransHistoryDao(){
		super(TransHistory.class);
	}
	
	public List<TransHistory> getPkgListOrderByAccTime(TransPackage transPackage) {
		return findBy("pkg", transPackage, "actTime", false);
	}
}
