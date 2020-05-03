package ts.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="ListTransHistory")
public class ListTransHistory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4941503468985892397L;
	public ListTransHistory() {
		// TODO Auto-generated constructor stub
	}
	private List<TransHistory> transHistoryList;
	
	public String toString() {
		return toString(false);
	}
	private String toString(boolean b) {
		// TODO Auto-generated method stub
		if(b) {
			return "null";
		}
		else {
			StringBuffer sb = new StringBuffer();
	        if(transHistoryList != null){
	            for(TransHistory transHistory:transHistoryList){
	                sb.append(transHistory.toString());
	            }
	        }
	        return sb.toString();
		}
		
	}
	public List<TransHistory> getTransHistoryList() {
		return transHistoryList;
	}
	public void setTransHistoryList(List<TransHistory> transHistoryList) {
		this.transHistoryList = transHistoryList;
	}
	
	

}
