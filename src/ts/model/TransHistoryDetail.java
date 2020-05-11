package ts.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="TransHistoryDetail")
public class TransHistoryDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3366396099638993504L;
	
	public TransHistoryDetail() {
		// TODO Auto-generated constructor stub
	}
	private ExpressSheet expressSheet;
    private TransHistory transHistory;
    private  UserInfo UIDFrom;
    private  UserInfo UIDTo;
    private TransNode fromNode;
    private TransNode toNode;

    public void setExpressSheet(ExpressSheet expressSheet) {
        this.expressSheet = expressSheet;
    }

    public ExpressSheet getExpressSheet() {
        return expressSheet;
    }

    public void setTransHistory(TransHistory transHistory) {
        this.transHistory = transHistory;
    }

    public void setUIDFrom(UserInfo UIDFrom) {
        this.UIDFrom = UIDFrom;
    }

    public void setUIDTo(UserInfo UIDTo) {
        this.UIDTo = UIDTo;
    }

    public TransHistory getTransHistory() {
        return transHistory;
    }

    public UserInfo getUIDFrom() {
        return UIDFrom;
    }

    public UserInfo getUIDTo() {
        return UIDTo;
    }

    public void setFromNode(TransNode fromNode) {
        this.fromNode = fromNode;
    }

    public void setToNode(TransNode toNode) {
        this.toNode = toNode;
    }

    public TransNode getFromNode() {
        return fromNode;
    }

    public TransNode getToNode() {
        return toNode;
    }
}
