package action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import model.Goods;
import model.Stock;
import util.DbUtil;

import com.opensymphony.xwork2.ActionSupport;

import dao.GoodsDao;

public class BarCharAction extends ActionSupport{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private String id;
	private Goods goods;
	private JFreeChart chart;
	
	public JFreeChart getChart() {
		return chart;
	}
	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	DbUtil dbUtil = new DbUtil();
	GoodsDao goodsDao = new GoodsDao();
	
	public String show() throws Exception{
		//����ʵ�ֶ�̬���ݱ���
		//request.setAttribute("mainPage", "/view/show.jsp");
		/*Connection con=dbUtil.getCon();
		goods = goodsDao.getGoodsById(con, id);//��ȡ�����Ʒ����Ϣ
*/				
		//��������
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(510, "���", "java01");
		dataset.addValue(320, "������", "java01");
		chart=ChartFactory.createBarChart3D("��Ʒ������۱�", "���", "", dataset,
				PlotOrientation.VERTICAL, true, true, true);
		CategoryPlot plot=chart.getCategoryPlot();
		// ��ʾÿ��������ֵ�����޸ĸ���ֵ����������
		BarRenderer3D renderer=new BarRenderer3D();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setItemLabelAnchorOffset(10D);  
		// ����ƽ������֮�����
		renderer.setItemMargin(0.4);
		
		plot.setRenderer(renderer);
				
		return "success";
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}

}
