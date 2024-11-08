import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FeedbackDialog extends JDialog {
    public FeedbackDialog(JFrame parent, List<Item> incorrectItems) {
        super(parent, "게임 종료 - 피드백", true);
        
        // 다이얼로그 레이아웃 설정
        setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = new JLabel("잘못된 분리수거 항목");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);
        
        // 잘못된 항목을 하나씩 표시
        for (Item item : incorrectItems) {
            String message = item.getName() + " 용기는 " + item.getType() + " 수거함에 배출해야 합니다.";
            JLabel itemLabel = new JLabel(message);
            itemLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            itemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(itemLabel);
        }
        
        // 닫기 버튼 추가
        JButton closeButton = new JButton("닫기");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> setVisible(false));
        
        contentPanel.add(Box.createVerticalStrut(10)); // 간격 추가
        contentPanel.add(closeButton);
        
        add(contentPanel, BorderLayout.CENTER);
        pack(); // 다이얼로그 크기 자동 조절
        setLocationRelativeTo(parent); // 부모 프레임 중앙에 다이얼로그 표시
    }
}
