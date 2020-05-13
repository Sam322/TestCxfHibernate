
package ts.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import ts.daoImpl.CustomerInfoDao;
import ts.daoImpl.ExpressSheetDao;
import ts.daoImpl.PackageRouteDao;
import ts.daoImpl.TransHistoryDao;
import ts.daoImpl.TransNodeDao;
import ts.daoImpl.TransPackageContentDao;
import ts.daoImpl.TransPackageDao;
import ts.daoImpl.UserInfoDao;
import ts.model.CustomerInfo;
import ts.model.ExpressSheet;
import ts.model.ListTransHistory;
import ts.model.ListTransPackge;
import ts.model.PackageRoute;
import ts.model.TransHistory;
import ts.model.TransHistoryDetail;
import ts.model.TransNode;
import ts.model.TransPackage;
import ts.model.TransPackageContent;
import ts.model.UserInfo;
import ts.serviceInterface.IDomainService;

public class DomainService implements IDomainService {

	private ExpressSheetDao expressSheetDao;
	private TransPackageDao transPackageDao;
	private TransHistoryDao transHistoryDao;
	private TransPackageContentDao transPackageContentDao;
	private PackageRouteDao packageRouteDao;
	private UserInfoDao userInfoDao;
	private TransNodeDao transNodeDao;
	private CustomerInfoDao customerInfoDao;

	public CustomerInfoDao getCustomerInfoDao() {
		return customerInfoDao;
	}

	public void setCustomerInfoDao(CustomerInfoDao customerInfoDao) {
		this.customerInfoDao = customerInfoDao;
	}

	public TransNodeDao getTransNodeDao() {
		return transNodeDao;
	}

	public void setTransNodeDao(TransNodeDao transNodeDao) {
		this.transNodeDao = transNodeDao;
	}

	public ExpressSheetDao getExpressSheetDao() {
		return expressSheetDao;
	}

	public void setExpressSheetDao(ExpressSheetDao dao) {
		this.expressSheetDao = dao;
	}

	public PackageRouteDao getPackageRouteDao() {
		return packageRouteDao;
	}

	public void setPackageRouteDao(PackageRouteDao packageRouteDao) {
		this.packageRouteDao = packageRouteDao;
	}

	public TransPackageDao getTransPackageDao() {
		return transPackageDao;
	}

	public void setTransPackageDao(TransPackageDao dao) {
		this.transPackageDao = dao;
	}

	public TransHistoryDao getTransHistoryDao() {
		return transHistoryDao;
	}

	public void setTransHistoryDao(TransHistoryDao dao) {
		this.transHistoryDao = dao;
	}

	public TransPackageContentDao getTransPackageContentDao() {
		return transPackageContentDao;
	}

	public void setTransPackageContentDao(TransPackageContentDao dao) {
		this.transPackageContentDao = dao;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao dao) {
		this.userInfoDao = dao;
	}

	public Date getCurrentDate() {
		// 产生一个不带毫秒的时间,不然,SQL时间和JAVA时间格式不一致
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date tm = new Date();
		try {
			tm = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return tm;
	}

	/**
	 * ldq 获取所有运单信息
	 */
	@Override
	public List<ExpressSheet> getAllExpressList() {
		return expressSheetDao.getAll();
	}

	/**
	 * ldq 删除所选运单（id）
	 */
	@Override
	public Response deleteExpressSheet(String id) {
		expressSheetDao.removeById(id);
		return Response.ok("Deleted").header("EntityClass", "D_ExpressSheet").build();
	}

	/**
	 * ldq 根据收货人姓名查询运单
	 */
	@Override
	public List<ExpressSheet> getExpressSheetbyrecevername(String recevername) {
		return null;
	}

	@Override
	public List<ExpressSheet> getExpressList(String property, String restrictions, String value) {
		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
		switch (restrictions.toLowerCase()) {
		case "eq":
			list = expressSheetDao.findBy(property, value, "ID", true);
			break;
		case "like":
			list = expressSheetDao.findLike(property, value + "%", "ID", true);
			break;
		}
		return list;
	}
//	@Override
//	public List<ExpressSheet> getExpressList(String property,
//			String restrictions, String value) {
//		Criterion cr1;
//		Criterion cr2 = Restrictions.eq("Status", 0);
//
//		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
//		switch(restrictions.toLowerCase()){
//		case "eq":
//			cr1 = Restrictions.eq(property, value);
//			break;
//		case "like":
//			cr1 = Restrictions.like(property, value);
//			break;
//		default:
//			cr1 = Restrictions.like(property, value);
//			break;
//		}
//		list = expressSheetDao.findBy("ID", true,cr1,cr2);		
//		return list;
//	}

	@Override
	public List<ExpressSheet> getExpressListInPackage(String packageId) {
		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
		try {
			list = expressSheetDao.getListInPackage(packageId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public Response getExpressSheet(String id) {
		ExpressSheet es = expressSheetDao.get(id);
		return Response.ok(es).header("EntityClass", "ExpressSheet").build();
	}

	@Override
	public Response newExpressSheet(String id, int uid) {
		ExpressSheet es = null;
		try {
			es = expressSheetDao.get(id);
		} catch (Exception e1) {
		}

		if (es != null) {
//			if(es.getStatus() != 0)
//				return Response.ok(es).header("EntityClass", "L_ExpressSheet").build(); //已经存在,且不能更改
//			else
			return Response.ok("快件运单信息已经存在!\n无法创建!").header("EntityClass", "E_ExpressSheet").build(); // 已经存在
		}
		try {
			String pkgId = userInfoDao.get(uid).getReceivePackageID();
			ExpressSheet nes = new ExpressSheet();
			nes.setID(id);
			nes.setType(0);
			nes.setAccepter(String.valueOf(uid));
			nes.setAccepteTime(getCurrentDate());
			nes.setStatus(ExpressSheet.STATUS.STATUS_CREATED);
//			TransPackageContent pkg_add = new TransPackageContent();
//			pkg_add.setPkg(transPackageDao.get(pkgId));
//			pkg_add.setExpress(nes);
//			nes.getTransPackageContent().add(pkg_add);
			expressSheetDao.save(nes);
			// 放到收件包裹中
			MoveExpressIntoPackage(nes.getID(), pkgId);
			return Response.ok(nes).header("EntityClass", "ExpressSheet").build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	/**
	 * ldq 编辑运单
	 */
	@Override
	public Response editExpressSheet(ExpressSheet obj) {
		System.out.println("调用了保存运单方法！");
		System.out.println(obj);
		try {
			// ExpressSheet nes = expressSheetDao.get(obj.getID());
			/*
			 * if (obj.getStatus() != ExpressSheet.STATUS.STATUS_CREATED) { return
			 * Response.ok("快件运单已付运!无法保存更改!").header("EntityClass",
			 * "E_ExpressSheet").build(); }
			 */
			expressSheetDao.save(obj);
			return Response.ok(obj).header("EntityClass", "R_ExpressSheet").build();
		} catch (Exception e) {
			System.out.println(e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	/**
	 * ldq 保存运单
	 */
	@Override
	public Response saveExpressSheet(ExpressSheet obj) {
		System.out.println("调用了保存运单方法！");
		System.out.println(obj);

		ExpressSheet es = null;
		try {
			es = expressSheetDao.get(obj.getID());
		} catch (Exception e1) {
		}
		if (es != null) {
			return Response.ok("Error").header("EntityClass", "E_ExpressSheet").build(); // 已经存在
		}

		try {
			// ExpressSheet nes = expressSheetDao.get(obj.getID());
			if (obj.getStatus() != ExpressSheet.STATUS.STATUS_CREATED) {
				return Response.ok("快件运单已付运!无法保存更改!").header("EntityClass", "E_ExpressSheet").build();
			}
			expressSheetDao.save(obj);
			return Response.ok(obj).header("EntityClass", "R_ExpressSheet").build();
		} catch (Exception e) {
			System.out.println(e);
			return Response.ok("Error").entity(e.getMessage()).build();
		}
	}

	@Override
	public Response ReceiveExpressSheetId(String id, int uid) {
		try {
			ExpressSheet nes = expressSheetDao.get(id);
			if (nes.getStatus() != ExpressSheet.STATUS.STATUS_CREATED) {
				return Response.ok("快件运单状态错误!无法收件!").header("EntityClass", "E_ExpressSheet").build();
			}
			nes.setAccepter(String.valueOf(uid));
			nes.setAccepteTime(getCurrentDate());
			nes.setStatus(ExpressSheet.STATUS.STATUS_DAIZHUAYUN); // 待揽收后待转运
			expressSheetDao.save(nes);
			return Response.ok(nes).header("EntityClass", "ExpressSheet").build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@Override
	public Response DispatchExpressSheet(String id, int uid) {
		// TODO Auto-generated method stub
		return null;
	}

	// lyy修改
	public Response MoveExpressIntoPackage(String id, String targetPkgId) {

		TransPackage targetPkg = transPackageDao.get(targetPkgId);
		if ((targetPkg.getStatus() > 0) && (targetPkg.getStatus() < 3)) { // 包裹的状态快点定义,打开的包裹或者货篮才能操作==================================================================
			return Response.ok("fasle").header("EntityClass", "MoveExpressIntoPackage").build();
		}

		TransPackageContent pkg_add = new TransPackageContent();
		pkg_add.setPkg(targetPkg);
		pkg_add.setExpress(expressSheetDao.get(id));
		pkg_add.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
		transPackageContentDao.save(pkg_add);
		return Response.ok("true").header("EntityClass", "MoveExpressIntoPackage").build();
	}

	public boolean MoveExpressBetweenPackage(String id, String sourcePkgId, String targetPkgId) {
		// 需要加入事务机制
		MoveExpressFromPackage(id, sourcePkgId);
		MoveExpressIntoPackage(id, targetPkgId);
		return true;
	}

	@Override
	public Response DeliveryExpressSheetId(String id, int uid) {
		try {
			String pkgId = userInfoDao.get(uid).getDelivePackageID();
			ExpressSheet nes = expressSheetDao.get(id);
			if (nes.getStatus() != ExpressSheet.STATUS.STATUS_TRANSPORT) {
				return Response.ok("快件运单状态错误!无法交付").header("EntityClass", "E_ExpressSheet").build();
			}

			if (transPackageContentDao.getSn(id, pkgId) == 0) {
				// 临时的一个处理方式,断路了包裹的传递过程,自己的货篮倒腾一下
				MoveExpressBetweenPackage(id, userInfoDao.get(uid).getReceivePackageID(), pkgId);
				return Response.ok("快件运单状态错误!\n快件信息没在您的派件包裹中!").header("EntityClass", "E_ExpressSheet").build();
			}

			nes.setDeliver(String.valueOf(uid));
			nes.setDeliveTime(getCurrentDate());
			nes.setStatus(ExpressSheet.STATUS.STATUS_DELIVERIED);
			expressSheetDao.save(nes);
			// 从派件包裹中删除
			MoveExpressFromPackage(nes.getID(), pkgId);
			// 快件没有历史记录,很难给出收件和交付的记录
			return Response.ok(nes).header("EntityClass", "ExpressSheet").build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@Override
	public List<TransPackage> getTransPackageList(String property, String restrictions, String value) {
		List<TransPackage> list = new ArrayList<TransPackage>();
		switch (restrictions.toLowerCase()) {
		case "eq":
			list = transPackageDao.findBy(property, value, "ID", true);
			break;
		case "like":
			list = transPackageDao.findLike(property, value + "%", "ID", true);
			break;
		}
		return list;
	}

	/**
	 * ldq 获取所有包裹信息
	 */
	@Override
	public List<TransPackage> getAllTransPackage() {
		return transPackageDao.getAll();
	}

	@Override
	public Response getTransPackage(String id) {
		TransPackage es = null;
		try {
			es = transPackageDao.get(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (es == null)
			return Response.ok("包裹不存在！").header("EntityClass", "N_TransPackage").build();
		return Response.ok(es).header("EntityClass", "TransPackage").build();
	}

	/**
	 * ldq 保存包裹
	 */
	@Override
	public Response saveTransPackage(TransPackage obj) {
		TransPackage tg = null;

		try {
			tg = transPackageDao.get(obj.getID());
		} catch (Exception e1) {
			System.out.println(e1);
		}

		if (tg != null) {
			return Response.ok("Error").header("EntityClass", "R_TransPackage").build();
		}

		try {
			transPackageDao.save(obj);
			return Response.ok(obj).header("EntityClass", "R_TransPackage").build();
		} catch (Exception e) {
			return Response.ok("Error").entity(e.getMessage()).build();
		}
	}

	/**
	 * ldq 编辑包裹
	 */
	@Override
	public Response editTransPackage(TransPackage obj) {
		try {
			transPackageDao.save(obj);
			return Response.ok(obj).header("EntityClass", "R_TransPackage").build();
		} catch (Exception e) {
			return Response.ok("Error").entity(e.getMessage()).build();
		}
	}

	// 快件历史=======================================================================
	public List<TransPackage> findTransPackagebyExpressSheetId(String id) {
		return transPackageDao.findbyExpressSheetId(id);
	}

	// ldq
	public List<TransHistory> getTransHistory(String id) {
		TransPackage transPackage = transPackageDao.get(id);
		System.out.println(transPackage);
		return transHistoryDao.getPkgListOrderByAccTime(transPackage);
	}

	// lyy 修改
	public Response MoveExpressFromPackage(String id, String sourcePkgId) {
		TransPackage sourcePkg = transPackageDao.get(sourcePkgId);
		if ((sourcePkg.getStatus() > 0) && (sourcePkg.getStatus() < 3)) {
			Response.ok("Deleted1").header("EntityClass", "MoveExpressFromPackage").build();
		}

		TransPackageContent pkg_add = transPackageContentDao.get(id, sourcePkgId);
		pkg_add.setStatus(TransPackageContent.STATUS.STATUS_OUTOF_PACKAGE);
		transPackageContentDao.save(pkg_add);
		return Response.ok("Deleted").header("EntityClass", "MoveExpressFromPackage").build();
	}

	// lyy 新增
	public Response DeleteExpressFromPackage(String id, String PkgId) {
		TransPackage sourcePkg = transPackageDao.get(PkgId);
		if ((sourcePkg.getStatus() != TransPackage.PKG_NEW)) {
			Response.ok("Deleted1").header("EntityClass", "MoveExpressFromPackage").build();
		}
		TransPackageContent pkg_add = transPackageContentDao.get(id, PkgId);
		transPackageContentDao.remove(pkg_add);
		return Response.ok("Deleted").header("EntityClass", "MoveExpressFromPackage").build();
	}

	// lyy 修改新建 作用：添加一个包裹历史addOneTransHistory
	@Override
	public Response addOneTransHistory(TransHistory transHistory) {
		System.out.println("执行了这个方法addOneTransHistory");
		TransPackage transPackage = transHistory.getPkg();
		transHistory.setActTime(getCurrentDate());
		if (transPackage == null) {
			return Response.ok("把包裹不存在！").header("EntityClass", "N_TransPackage").build();
		}
		transHistoryDao.save(transHistory);
		return Response.ok(transHistory).header("EntityClass", "TransHistory").build();
	}

	// lyy 新建 作用：获取一个包裹的transHistorylist
	@Override
	public Set<TransHistory> getTransHistoryFromList(String pkgId) {
		System.out.println("执行了这个方法getTransHistoryFromList");
		TransPackage transPackage = transPackageDao.get(pkgId);
		Set<TransHistory> transHistoryList = transPackage.getHistory();
		return transHistoryList;
	}

//包裹相关
	// lyy 修改
	@Override
	public Response newTransPackage(TransPackage transPackage) {

		TransPackage tpk = null;
		try {
			tpk = transPackageDao.get(transPackage.getID());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (tpk != null) {
			return Response.ok("包裹id已存在").header("EntityClass", "E_TransPackage").build();
		}
		try {
			transPackageDao.save(transPackage);
			return Response.ok(transPackage).header("EntityClass", "TransPackage").build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	// lyy 新增saveTransPackage

	public Response saveOneTransPackage(TransPackage transPackage) {
		try {
			transPackageDao.save(transPackage);
		} catch (Exception e) {
			// TODO: handle exception
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok(transPackage).header("EntityClass", "R_TransPackage").build();
	}

	// lyy 新建 确认包裹，改变包裹状态
	@Override
	public Response accPkgAndChangStatus(String pkgId) {

		System.out.println("执行了这个方法accPkgAndChangStatus");
		TransPackage transPackage = transPackageDao.get(pkgId);
		if (transPackage == null) {
			return Response.ok("把包裹不存在！").header("EntityClass", "N_TransPackage").build();
		}
		transPackage.setStatus(3); // 3代表转运中心已确认。
		transPackageDao.save(transPackage);
		return Response.ok(transPackage).header("EntityClass", "TransPackage").build();
	}

	// lyy 新增 改变包裹状态为status
	public Response changeTransPackageStatus(TransPackage transPackage, int status) {
		System.out.println("执行了这个方法！changeTransPackageStatus" + transPackage.toString() + status);
		try {
			transPackage.setStatus(status);
			transPackageDao.save(transPackage);
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok(transPackage).header("EntityClass", "TransPackage").build();
	}

	// lyy 新建
	public Response saveTransHistoryList(ListTransHistory transHistories) {
		System.out.println("执行了这个方法！saveTransHistoryList" + transHistories.toString());
		Date date = getCurrentDate();
		List<TransHistory> transHistories2 = transHistories.getTransHistoryList();
		TransPackage transPackage = null;
		try {
			for (TransHistory transHistory : transHistories2) {
				transHistory.setActTime(date);
				transHistoryDao.save(transHistory);
//					//改变包裹状态
//					transPackage = transHistory.getPkg();
//					transPackage.setStatus(TransPackage.PKG_TRSNSIT);
//					transPackageDao.save(transPackage);
			}
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}

		return Response.ok("添加成功").header("EntityClass", "successed").build();
	}

	// lyy新增 改变包裹列表中所有包裹状态为status
	public Response changeTransPackageListStatus(ListTransPackge listTransPackge, int status) {
		System.out.println("执行了这个方法！changeTransPackageListStatus" + status);
		List<TransPackage> transPackages = listTransPackge.getTransPackageList();
		try {
			if (transPackages.size() != 0) {
				for (TransPackage transPackage : transPackages) {
					// System.out.println(transPackage.toString());
					transPackage.setStatus(status);
					transPackageDao.save(transPackage);
				}
			}
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}

		return Response.ok("改变成功").header("EntityClass", "successed").build();

	}

	// lyy 新增 改变包裹列表里的快件的状态
	public Response changeExpressStatusInTransPackageList(ListTransPackge listTransPackge, int yuan_status,
			int mubiao_status) {
		System.out.println("执行了这个方法！changeExpressStatusInTransPackageList" + yuan_status);
		List<TransPackage> transPackages = listTransPackge.getTransPackageList();
		try {
			if (transPackages.size() != 0) {
				for (TransPackage transPackage : transPackages) {
					// System.out.println(transPackage.toString());
					// 一个包裹找到很多快件
					List<TransPackageContent> list;
					list = transPackageContentDao.getTransPackageContents(transPackage, yuan_status);
					if (list.size() != 0) {
						for (TransPackageContent pc : list) { // 对于包裹里的每一个快件改变其状态
							// System.out.println("执行了这个方法！"+pc.toString());
							ExpressSheet es = pc.getExpress();
							es.setStatus(mubiao_status); // 改变快件状态
							expressSheetDao.save(es);
						}
					}

				}
			}
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}

		return Response.ok("改变成功").header("EntityClass", "successed").build();

	}

	// lyy 新增d
	public Response getRecentOneTranHistory(TransPackage transPackage) {
		System.out.println("执行了这个方法getRecentOneTranHistory");
		List<TransHistory> transHistories = null;
		TransHistory transHistory = null;
		try {
			transHistories = transHistoryDao.getPkgListOrderByAccTime(transPackage);
			transHistory = transHistories.get(0);
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok(transHistory).header("EntityClass", "TransHistory").build();
	}

	// lyy 新增
	public Response saveOnePackageRoute(PackageRoute packageRoute) {
		System.out.println("执行了这个方法saveOnePackageRoute" + packageRoute.toString());
		try {
			packageRoute.setTm(getCurrentDate());
			packageRouteDao.save(packageRoute);
		} catch (Exception e) {
			// TODO: handle exception
			return Response.serverError().entity(e.getMessage()).build();
		}

		return Response.ok(packageRoute).header("EntityClass", "PackageRoute").build();
	}

	// lyy新增
	@Override
	public Response saveListPackageRoute(ListTransPackge tListTransPackge, float x, float y) {
		System.out.println("执行了这个方法saveListPackageRoute" + tListTransPackge.toString() + "/" + x + "/" + y);
		List<TransPackage> lTransPackages = tListTransPackge.getTransPackageList();
		Date date = getCurrentDate();
		try {
			for (TransPackage transPackage : lTransPackages) {
				PackageRoute packageRoute = new PackageRoute();
				packageRoute.setPkg(transPackage);
				packageRoute.setTm(date);
				packageRoute.setX(x);
				packageRoute.setY(y);
				packageRouteDao.save(packageRoute);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return Response.serverError().entity(e.getMessage()).build();
		}

		return Response.ok("修改成功").header("EntityClass", "ListPackageRoute").build();
	}

	// lyy 新增
	public Response getTransPackageContent(String pkgID, String expressID) {
		TransPackageContent transPackageContent = null;
		try {
			transPackageContent = transPackageContentDao.get(expressID, pkgID);
		} catch (Exception e) {
			// TODO: handle exception
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok(transPackageContent).header("EntityClass", "TransPackageContent").build();
	}

	// lyy 新增
	public Response changeExpressStatusInPackage(String pkgID, String expressID, int status) {
		TransPackageContent transPackageContent = null;
		try {
			transPackageContent = transPackageContentDao.get(expressID, pkgID);
			transPackageContent.setStatus(status);
			transPackageContentDao.save(transPackageContent);
		} catch (Exception e) {
			// TODO: handle exception
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok(transPackageContent).header("EntityClass", "TransPackageContent").build();
	}

	// lyy 新增
	public Response changeExpressStatusInTransPackage(String pkgID, int status) {
		try {
			List<ExpressSheet> lExpressSheets = expressSheetDao.getListInPackage(pkgID);
			for (ExpressSheet es : lExpressSheets) {
				TransPackageContent transPackageContent = transPackageContentDao.get(es.getID(), pkgID);
				transPackageContent.setStatus(status);
				transPackageContentDao.save(transPackageContent);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok("改变状态成功！").header("EntityClass", "ChangeExpressStatus").build();
	}

	// lyy 新增
	public List<TransHistoryDetail> getTransHistoryDetailList(String expressID) {
		ExpressSheet es = expressSheetDao.get(expressID);
		List<TransPackage> transPackages = transPackageDao.findbyExpressSheetIdList(expressID);
		List<TransHistoryDetail> transHistoryDetails = new ArrayList<TransHistoryDetail>();
		int count = 0;
		if (es.getStatus() == ExpressSheet.STATUS.STATUS_CREATED) {
			TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
			transHistoryDetail.setExpressSheet(es);
			transHistoryDetail.setSN(count++);
			transHistoryDetails.add(transHistoryDetail);
		} else if (es.getStatus() == ExpressSheet.STATUS.STATUS_DAIZHUAYUN) {
			for (int i = 0; i <= 1; i++) {
				TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
				transHistoryDetail.setExpressSheet(es);
				transHistoryDetail.setSN(count++);
				transHistoryDetails.add(transHistoryDetail);
			}
		} else if (es.getStatus() == ExpressSheet.STATUS.STATUS_TRANSPORT) {
			for (int i = 0; i <= 1; i++) {
				TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
				transHistoryDetail.setExpressSheet(es);
				transHistoryDetail.setSN(count++);
				transHistoryDetails.add(transHistoryDetail);
			}
			for (TransPackage transPackage : transPackages) {
				List<TransHistory> transHistories = transHistoryDao.getPkgListOrderByAscTime(transPackage);
				for (TransHistory transHistory : transHistories) {
					TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
					transHistoryDetail.setExpressSheet(es);
					UserInfo uidfrom = userInfoDao.get(transHistory.getUIDFrom());
					UserInfo uidto = userInfoDao.get(transHistory.getUIDTo());
					TransNode fromNode = transNodeDao.get(uidfrom.getDptID());
					TransNode toNode = transNodeDao.get(uidto.getDptID());
					transHistoryDetail.setFromNode(fromNode);
					transHistoryDetail.setToNode(toNode);
					transHistoryDetail.setTransHistory(transHistory);
					transHistoryDetail.setUIDFrom(uidfrom);
					transHistoryDetail.setUIDTo(uidto);
					transHistoryDetail.setSN(count++);
					transHistoryDetails.add(transHistoryDetail);
				}
			}
		} else if (es.getStatus() == ExpressSheet.STATUS.STATUS_PAISONG) {
			for (int i = 0; i <= 1; i++) {
				TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
				transHistoryDetail.setExpressSheet(es);
				transHistoryDetail.setSN(count++);
				transHistoryDetails.add(transHistoryDetail);
			}
			for (TransPackage transPackage : transPackages) {
				List<TransHistory> transHistories = transHistoryDao.getPkgListOrderByAscTime(transPackage);
				for (TransHistory transHistory : transHistories) {
					TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
					transHistoryDetail.setExpressSheet(es);
					UserInfo uidfrom = userInfoDao.get(transHistory.getUIDFrom());
					UserInfo uidto = userInfoDao.get(transHistory.getUIDTo());
					TransNode fromNode = transNodeDao.get(uidfrom.getDptID());
					TransNode toNode = transNodeDao.get(uidto.getDptID());
					transHistoryDetail.setFromNode(fromNode);
					transHistoryDetail.setToNode(toNode);
					transHistoryDetail.setTransHistory(transHistory);
					transHistoryDetail.setUIDFrom(uidfrom);
					transHistoryDetail.setUIDTo(uidto);
					transHistoryDetail.setSN(count++);
					transHistoryDetails.add(transHistoryDetail);
				}
			}
			TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
			transHistoryDetail.setUIDFrom(userInfoDao.get(Integer.getInteger(es.getDeliver())));
			transHistoryDetail.setSN(count++);
			transHistoryDetails.add(transHistoryDetail);

		} else if (es.getStatus() == ExpressSheet.STATUS.STATUS_DELIVERIED) {
			for (int i = 0; i <= 1; i++) {
				TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
				transHistoryDetail.setExpressSheet(es);
				transHistoryDetail.setSN(count++);
				transHistoryDetails.add(transHistoryDetail);
			}
			for (TransPackage transPackage : transPackages) {
				List<TransHistory> transHistories = transHistoryDao.getPkgListOrderByAscTime(transPackage);
				for (TransHistory transHistory : transHistories) {
					TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
					transHistoryDetail.setExpressSheet(es);
					UserInfo uidfrom = userInfoDao.get(transHistory.getUIDFrom());
					UserInfo uidto = userInfoDao.get(transHistory.getUIDTo());
					TransNode fromNode = transNodeDao.get(uidfrom.getDptID());
					TransNode toNode = transNodeDao.get(uidto.getDptID());
					transHistoryDetail.setFromNode(fromNode);
					transHistoryDetail.setToNode(toNode);
					transHistoryDetail.setTransHistory(transHistory);
					transHistoryDetail.setUIDFrom(uidfrom);
					transHistoryDetail.setUIDTo(uidto);
					transHistoryDetail.setSN(count++);
					transHistoryDetails.add(transHistoryDetail);
				}
			}
			TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
			transHistoryDetail.setUIDFrom(userInfoDao.get(Integer.getInteger(es.getDeliver())));
			transHistoryDetail.setSN(count++);
			transHistoryDetails.add(transHistoryDetail);

			TransHistoryDetail transHistoryDetail1 = new TransHistoryDetail();
			transHistoryDetail1.setUIDFrom(userInfoDao.get(Integer.getInteger(es.getDeliver())));
			transHistoryDetail.setSN(count++);
			transHistoryDetails.add(transHistoryDetail1);
		}

		return transHistoryDetails;

	}

	//tzx
	@Override
	public List<ExpressSheet> getExpressListbytransnode(String id,int status) {
		List<ExpressSheet> expressSheets = expressSheetDao.findBy("status", status, "ID", true);
		System.out.println(id);
		TransNode transNode = transNodeDao.get(id);
		String regionCode = transNode.getRegionCode();
		for (int i = 0; i < expressSheets.size(); i++) {
			if (status == 0) {
				if (!regionCode.equals(expressSheets.get(i).getSender().getRegionCode())) {
					expressSheets.remove(i);
					i--;
				}
			}else if (status == 5) {
				if (!regionCode.equals(expressSheets.get(i).getRecever().getRegionCode())) {
					expressSheets.remove(i);
					i--;
				}
			}
		}
		return expressSheets;
	}

	// ldq查询包裹的route
	@Override
	public List<PackageRoute> getPackageRoute(String packageID) {
		List<PackageRoute> packageRoutes = packageRouteDao.getAll();
		List<PackageRoute> Routes = new ArrayList<>();
		System.out.println(packageID);
		for (int i = 0; i < packageRoutes.size(); i++) {
			System.out.println(packageRoutes.get(i).getPkg().getID());
			if (packageRoutes.get(i).getPkg().getID().equals(packageID)) {
				Routes.add(packageRoutes.get(i));
			}
		}
		return Routes;
	}
	
	//lyy 新增
	@Override
	public List<PackageRoute> getPackageRouteListByExpressId(String expressID){
		List<PackageRoute> packageRoutes= new ArrayList<PackageRoute>();
		List<TransPackage> transPackages = transPackageDao.findbyExpressSheetIdList(expressID);
		for(TransPackage transPackage:transPackages) {
			List<PackageRoute> pRoutes = packageRouteDao.getPackageRouteListByPkg(transPackage);
			packageRoutes.addAll(pRoutes);
		}
		
		return packageRoutes;
	}
	

	// ldq 通过顾客姓名、联系方式模糊查询运单
	@Override
	public List<ExpressSheet> getExpressListbyCustomerinfo(String info) {
		List<ExpressSheet> expressSheets = new ArrayList<ExpressSheet>();
		List<CustomerInfo> customerInfos = new ArrayList<CustomerInfo>();
		System.out.println(info);
		if (info.matches("[0-9]+")) {// info为数字
			customerInfos = customerInfoDao.findLike("telCode", info+ "%", "ID", true);
		} else {
			customerInfos = customerInfoDao.findLike("name", info+ "%", "ID", true);
		}
		System.out.println(customerInfos);//运行到这里
		for (CustomerInfo customerInfo : customerInfos) {
			expressSheets.addAll(expressSheetDao.findBy("recever", customerInfo, "ID", true));
			expressSheets.addAll(expressSheetDao.findBy("sender", customerInfo, "ID", true));
		}
		return expressSheets;
	}
	// ldq 新增通过包裹id查询其中的快件
	@Override
	public List<ExpressSheet> getExpressListbytranspackageId(String transpackageId) {
		List<TransPackageContent> transPackageContents =  transPackageContentDao.findBy("pkg", transPackageDao.get(transpackageId), "SN", true);
		List<ExpressSheet> expressSheets = new ArrayList<ExpressSheet>();
		for (TransPackageContent transPackageContent : transPackageContents) {
			expressSheets.add(transPackageContent.getExpress());
		}
		return expressSheets;
	}

}
