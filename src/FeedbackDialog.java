import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FeedbackDialog extends JDialog {

    public FeedbackDialog(JFrame parent, int finalScore, List<Item> incorrectItems) {
        super(parent, "게임 종료 - 피드백", true);
        
        // 다이얼로그 레이아웃 설정
        setLayout(new BorderLayout());
       

        // 최종 점수 표시
        JLabel scoreLabel = new JLabel("최종 점수: " + finalScore);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(scoreLabel);
        contentPanel.add(Box.createVerticalStrut(10)); // 점수와 피드백 간격

        if (!incorrectItems.isEmpty()) {
            JLabel feedbackLabel = new JLabel("올바른 분리 수거 방법을 알아보아요! ");
            feedbackLabel.setFont(new Font("Arial", Font.BOLD, 16));
            feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(feedbackLabel);

            // 잘못된 항목 리스트 표시
            for (Item item : incorrectItems) {
                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));
                itemPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

                // 아이템 이미지
                JLabel imageLabel = new JLabel(new ImageIcon(item.getImage())); // 아이템의 이미지를 표시
                itemPanel.add(imageLabel);

                // 아이템 설명
                JLabel textLabel = new JLabel(item.getName() + " 용기는 " + item.getType() + " 수거함에 배출해야 합니다.");
                textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                itemPanel.add(Box.createHorizontalStrut(10)); // 이미지와 텍스트 간격
                itemPanel.add(textLabel);

                contentPanel.add(itemPanel);
                contentPanel.add(Box.createVerticalStrut(10)); // 항목 간 간격
            }
        } else {
            JLabel successLabel = new JLabel("모든 분리수거를 올바르게 수행했습니다! 잘하셨습니다!");
            successLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            successLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(successLabel);
        }
        
        // 스크롤 가능하도록 JScrollPane 추가
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setPreferredSize(new Dimension(500, 600)); // 다이얼로그 크기 설정
        add(scrollPane, BorderLayout.CENTER);


        // 닫기 버튼 추가
        JButton closeButton = new JButton("닫기");
        closeButton.addActionListener(e -> setVisible(false));
        add(closeButton, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(parent);
    }
}
