
package Controller;

import display.CustomerJPanel;
import display.HomeJPanel;
//import display.ImportProductJPanel;
import display.ProductJPanel;
import display.ReportJPanel;
import display.StoreHouseJPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.*;

/**
 *
 * @author Admin
 */
public class ChangeView {
    
    private JPanel root;
    private String kindSelected = "";
    
    private List<category.Category> listItem = null;

    public ChangeView(JPanel root) {
        this.root = root;
    }
    
    //
    public void setView(JPanel jpnItem, JLabel jlbItem){
        //Chọn màu mặc định cho Nút Trang Chủ
        kindSelected = "TrangChu";
        jpnItem.setBackground(Color.GREEN); 
        jlbItem.setBackground(Color.GREEN);
        
        
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new HomeJPanel()); //Giao Diện Trang chủ sẽ được hiển thị mặc định khi mở chương trình
        root.validate();
        root.repaint();
        
    }
    
    public void setEvent(List<category.Category> listItem){
        this.listItem = listItem;
        for(category.Category item : listItem){
            item.getJlb().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getJlb()));
        }//Cài đặt sự kiện chung cho bên MainJFrame, mỗi Category sẽ được hiển thị khi click vào Label or Pannel tương ứng
        
    }
    
    class LabelEvent implements MouseListener{
        //Cài đặt sự kiện khi Click Chuột vào Label
        private JPanel node;
        
        private String kind;
        private JPanel jpnItem;
        private JLabel jlbItem;

        public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }
        
        // Những cái Trangchu, SanPham,Kho,... được coi là biến của category để kiểm tra
        // Nếu biến trùng với biến tạm thì show ra Panel tương ứng

        @Override
        public void mouseClicked(MouseEvent e) {
            switch(kind){
                case "TrangChu":
                    node = new HomeJPanel();
                    break;
                case "SanPham":
                    node = new ProductJPanel();
                    break;
                case "Kho":
                    node = new StoreHouseJPanel();
                    break;
                case "KhachHang":
                    node = new CustomerJPanel();
                    break;
                case "BaoCao":
                    node = new ReportJPanel();
                    break;
                default:
                    break;
            }
            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
            setChangeBackGround(kind);
        }
        
        //Những sự kiện ở dưới là đổi màu khi Click hoặc chỉ chuột vào Label or Panel
        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind; //Khi CHỈ CHUỘC vao thi sẽ được đổi thành màu xanh lá
            jpnItem.setBackground(Color.GREEN);
            jlbItem.setBackground(Color.GREEN);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        
        }

        @Override
        public void mouseEntered(MouseEvent e) {
           //Khi chọn label hoặc panel sẽ đổi thành màu xanh lá
           jpnItem.setBackground(Color.GREEN);
           jlbItem.setBackground(Color.GREEN);
        }

        @Override
        public void mouseExited(MouseEvent e) {
           if(!kindSelected.equalsIgnoreCase(kind)){
               jpnItem.setBackground(Color.LIGHT_GRAY);
               jlbItem.setBackground(Color.LIGHT_GRAY);
               //Khi label hoặc Panel không được chọn thì sẽ chuyển thành màu xám
           }
        }
        
    }
     private void setChangeBackGround(String kind){
         for(category.Category item : listItem){
             if(item.getKind().equalsIgnoreCase(kind)){
                item.getJpn().setBackground(Color.GREEN);
                item.getJlb().setBackground(Color.GREEN);
             }else{
                item.getJpn().setBackground(Color.LIGHT_GRAY);
                item.getJlb().setBackground(Color.LIGHT_GRAY);
             }
         }
     }
     
     //Còn màu xanh dương khi mới bắt đầu mở chuong trình lên đã có là vì lúc kéo thả các label, panel đó được để mặc định màu xanh dương
}
