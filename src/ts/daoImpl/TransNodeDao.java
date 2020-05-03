package ts.daoImpl;

import java.util.List;

import org.springframework.util.Assert;

import ts.daoBase.BaseDao;
import ts.model.TransNode;

public class TransNodeDao extends BaseDao<TransNode, String>{
	public TransNodeDao(){
		super(TransNode.class);
	}

	public List<TransNode> findByRegionCode(String region_code) {
        Assert.hasText(region_code);
        return findBy("regionCode", region_code, "nodeName", true);
	}
	
	//lyy 新增
	public List<TransNode> findById(String id) {
		Assert.hasText(id);
		return findLike("ID", id+"%", "ID", true);
	}
	
	//lyy新增
	public List<TransNode> findByNodeName(String NodeName) {
		Assert.hasText(NodeName);
		return findLike("nodeName",NodeName,"ID",true);
	}

}
