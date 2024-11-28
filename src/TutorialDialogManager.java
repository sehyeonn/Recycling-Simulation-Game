import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TutorialDialogManager {
	// ***************************
	// TutorialDialogManager 클래스
	// 레벨 선택 후 게임 시작 전 해당 레벨의 구성과 아이템들 설명 튜토리얼을 관리
	// 다이얼로그들로 만들어 화면에 표시
	// ***************************

	private GameFrame gameFrame;

	private boolean isLevelDialogShown = false; // 레벨 다이얼로그가 한 번만 나타나도록 체크하는 변수

	public TutorialDialogManager(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}

	// 다이얼로그가 안나오는 현상으로 위해 resetLevelDialogShown를 추가해 초기화해야함..
	public void resetLevelDialogShown() {
		isLevelDialogShown = false;
	}

	public void showItemDialogs(LevelData levelData, int index, String level) {
		if (index >= levelData.getItemTemplates().size()) {
			return; // 모든 튜토리얼 다이얼로그를 보여준 후 게임 시작
		}

		// 처음에만 레벨 다이얼로그를 띄운다
		if (!isLevelDialogShown) {
			showLevelDialog(levelData); // 처음에만 레벨 다이얼로그 표시
			isLevelDialogShown = true; // 이후부터 레벨 다이얼로그는 표시되지 않도록 설정
		}

		Item item = levelData.getItemTemplates().get(index);
		ImageIcon itemIcon = (ImageIcon) item.getIcon();

		// 제목 이미지 로드 및 크기 조정
		ImageIcon titleImageIcon = new ImageIcon(getClass().getClassLoader().getResource("tutorial.png"));
		Image titleImage = titleImageIcon.getImage();
		int titleWidth = 300; // 원하는 너비
		int titleHeight = 70; // 원하는 높이
		Image resizedTitleImage = titleImage.getScaledInstance(titleWidth, titleHeight, Image.SCALE_SMOOTH);
		JLabel titleLabel = new JLabel(new ImageIcon(resizedTitleImage));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// 아이템 레이블 생성
		JLabel itemLabel = new JLabel(item.getName(), itemIcon, JLabel.CENTER);
		itemLabel.setVerticalTextPosition(JLabel.BOTTOM);
		itemLabel.setHorizontalTextPosition(JLabel.CENTER);

		String message = "";

		// 도구 레이블 생성 - 레벨 3에서만 추가되면 됨
		JLabel toolLabel = null;
		if (Integer.parseInt(level) >= 3 && item instanceof ComplexItem) {
			ComplexItem complexItem = (ComplexItem) item;
			URL toolImagePath = getClass().getClassLoader().getResource(complexItem.getNecessaryTool() + ".png");
			
			message = complexItem.getTutorialMessage();

			ImageIcon toolIcon = new ImageIcon(toolImagePath);
			toolLabel = new JLabel(complexItem.getNecessaryTool(), toolIcon, JLabel.CENTER);
			toolLabel.setVerticalTextPosition(JLabel.BOTTOM);
			toolLabel.setHorizontalTextPosition(JLabel.CENTER);
		} else {
			message = item.getTutorialMessage();
		}

		// 수평 박스 생성: 아이템과 도구를 나란히 배치
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.add(Box.createHorizontalStrut(20)); // 왼쪽 여백
		horizontalBox.add(itemLabel);
		if (toolLabel != null) {
			horizontalBox.add(Box.createHorizontalStrut(20)); // 아이템과 도구 간 여백
			horizontalBox.add(toolLabel);
		}
		horizontalBox.add(Box.createHorizontalStrut(20)); // 오른쪽 여백
		horizontalBox.setAlignmentX(Component.CENTER_ALIGNMENT);

		// 설명 레이블 생성
		JLabel instructionLabel = new JLabel(message, JLabel.CENTER);
		instructionLabel.setFont(StyleManager.fontMidiumBold);
		instructionLabel.setForeground(Color.BLACK);
		instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// 다이얼로그 박스 구성
		Box dialogBox = Box.createVerticalBox();
		dialogBox.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // 전체 여백 - 위, 좌, 아래, 우
		dialogBox.add(titleLabel);
		dialogBox.add(Box.createVerticalStrut(30)); // 제목과 수평 박스 간의 여백
		dialogBox.add(horizontalBox); // 아이템과 도구 추가
		dialogBox.add(Box.createVerticalStrut(15)); // 수평 박스와 설명 간의 여백
		dialogBox.add(instructionLabel);

		// 아이템 다이얼로그
		JDialog dialog = new JDialog(gameFrame, true);
		dialog.setUndecorated(false);
		dialog.setSize(750, 500);
		dialog.setLocationRelativeTo(null); // 위치 선정
		dialog.getContentPane().setBackground(StyleManager.tutorialBackgroudColor); // 다이얼로그 배경색 설정
		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.setLocationRelativeTo(gameFrame);

		// Next 버튼 생성 (크기 고정 및 정렬 조정)
		JButton nextButton = new JButton("다음");
		nextButton.setFocusPainted(false);
		nextButton.setPreferredSize(new Dimension(100, 40)); // 버튼 크기 유지
		nextButton.setBackground(StyleManager.buttonColor);
		nextButton.setForeground(Color.WHITE);
		nextButton.setFont(StyleManager.fontMidiumBold);
		nextButton.addActionListener(e -> {
			SoundManager.playSound("click");
			dialog.dispose();
			showItemDialogs(levelData, index + 1, level); // 다음 아이템 다이얼로그 호출
		});

		// 버튼을 포함하는 패널 생성
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10)); // 가운데 정렬, 여백 조정
		buttonPanel.setBackground(StyleManager.tutorialBackgroudColor); // 배경색 다이얼로그와 동일하게 설정
		buttonPanel.add(nextButton);

		// 다이얼로그에 구성 추가
		dialog.getContentPane().add(dialogBox, BorderLayout.CENTER);
		dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH); // 버튼 패널 추가
		dialog.setVisible(true);
	}

	// 레벨 설명 다이얼로그를 표시하는 메소드
	private void showLevelDialog(LevelData levelData) {
		String dialogMessage = levelData.getMessage(); // 레벨 설명
		ImageIcon titleImageIcon = new ImageIcon(getClass().getClassLoader().getResource("level" + levelData.getLevel() + ".png"));
		ImageIcon levelImageIcon = new ImageIcon(getClass().getClassLoader().getResource("level" + levelData.getLevel() + "_image.png"));

		// 레벨 메세지 메서드 디자인
		JDialog levelDialog = new JDialog(gameFrame, true);
		levelDialog.setUndecorated(true); // 다이얼 로그 특유의 아이콘, 제목 없애기
		levelDialog.getContentPane().setBackground(StyleManager.tutorialBackgroudColor);
		levelDialog.setSize(750, 600);
		levelDialog.setLocationRelativeTo(gameFrame); // 화면 중앙에 표시

		// 전체 레이아웃을 GridBagLayout으로..
		levelDialog.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER; // 중앙 정렬
		gbc.insets = new Insets(10, 10, 10, 10); // 컴포넌트 사이 여백 추가

		// 제목 이미지를 레이블로 추가
		JLabel titleLabel = new JLabel(titleImageIcon);
		gbc.gridx = 0;
		gbc.gridy = 0;
		levelDialog.add(titleLabel, gbc);

		// 이미지 레이블
		Image img = levelImageIcon.getImage();

		// 이미지 크기 조정 (원하는 크기로)
		int newWidth = 300; // 원하는 너비
		int newHeight = 250; // 원하는 높이
		Image scaledImage = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

		JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
		gbc.gridx = 0;
		gbc.gridy = 1;
		levelDialog.add(imageLabel, gbc);

		// 메시지 레이블
		JLabel messageLabel = new JLabel(
				"<html><div style='text-align: center;'>" + dialogMessage.replaceAll("\r\n", "<br>") + "</div></html>");
		messageLabel.setFont(StyleManager.fontMidiumBold);
		messageLabel.setForeground(Color.BLACK);
		gbc.gridx = 0;
		gbc.gridy = 2;
		levelDialog.add(messageLabel, gbc);

		// 버튼 구성
		JButton nextButton = new JButton("다음");
		nextButton.setFocusPainted(false);
		nextButton.setPreferredSize(new Dimension(100, 40));
		nextButton.setBackground(StyleManager.buttonColor);
		nextButton.setForeground(Color.WHITE);
		nextButton.setFont(StyleManager.fontMidiumBold);
		nextButton.addActionListener(e -> {
			SoundManager.playSound("click");
			levelDialog.dispose(); // 다이얼로그 닫기
		});

		gbc.gridx = 0;
		gbc.gridy = 3;
		levelDialog.add(nextButton, gbc);

		// 다이얼로그 표시
		levelDialog.setVisible(true);
	}

}
