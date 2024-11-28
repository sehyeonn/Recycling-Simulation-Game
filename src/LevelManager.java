import java.util.ArrayList;
import java.util.List;

public class LevelManager {
	// ***************************
	// LevelManager 클래스
	// 레벨에 따라 필요한 데이터를 제공
	// 데이터베이스 역할
	// ***************************
	
	public LevelData getLevelData(String level) {
		// 레벨을 받아 해당 레벨에 필요한 데이터 제공
		// 레벨이 추가되면 이곳에 case 문으로 추가
		switch (level) {
		case "1":
			return createDataLevel1();
		case "2":
			return createDataLevel2();
		case "3":
			return createDataLevel3();
		}
		return null;
	}

	private LevelData createDataLevel1() {
		// 레벨1 데이터
		List<Item> itemTemplates = new ArrayList<>();

		itemTemplates.add(new Item("물티슈 뚜껑", "플라스틱", "plastic_water.png"));
		itemTemplates.add(new Item("배달음식 용기", "플라스틱", "plastic_squre.png"));
		itemTemplates.add(new Item("테이크아웃 컵", "플라스틱", "plastic_cup.png"));
		
		itemTemplates.add(new Item("유리병", "유리", "glassbottle_sink_none.png"));
		itemTemplates.add(new Item("유리컵", "유리", "glassbottle_cup.png"));

		itemTemplates.add(new Item("참치캔", "캔류", "can_dong.png"));
		itemTemplates.add(new Item("빈 스팸 캔", "캔류", "can_spam.png"));

		List<Bin> bins = new ArrayList<>();
		bins.add(new Bin("플라스틱", "plastic_bin.png"));
		bins.add(new Bin("유리", "glass_bin.png"));
		bins.add(new Bin("캔류", "can_bin.png")); //캔으로 수정하기
		
		String message = "환영합니다, 분리배출 히어로!\r\n" + "첫 번째 임무는 기본 분리배출입니다.\r\n 주어진 아이템들을 올바른 분리배출함에 넣어보세요!";

		return new LevelData(1, itemTemplates, bins, message);
	}

	private LevelData createDataLevel2() {
		// 레벨2 데이터
		List<Item> itemTemplates = new ArrayList<>();

		itemTemplates.add(new Item("배달음식 용기", "플라스틱", "plastic_bowl.png"));
		itemTemplates.add(new Item("물티슈 뚜껑", "플라스틱", "plastic_water.png"));
		itemTemplates.add(new Item("배달음식 용기", "플라스틱", "plastic_squre.png"));
		
		itemTemplates.add(new Item("유리병", "유리", "glassbottle_sink_none.png"));
		itemTemplates.add(new Item("유리컵", "유리", "glassbottle_cup.png"));
		
		itemTemplates.add(new Item("종이", "종이", "paper.png"));
		itemTemplates.add(new Item("과자 상자", "종이", "snackbox.png"));
		itemTemplates.add(new Item("신문지", "종이", "paper_3.png"));
		
		itemTemplates.add(new Item("휴지", "일반", "tissue.png"));
		itemTemplates.add(new Item("프링글스 통", "일반", "pringles.png"));

		List<Bin> bins = new ArrayList<>();
		bins.add(new Bin("플라스틱", "plastic_bin.png"));
		bins.add(new Bin("유리", "glass_bin.png"));
		bins.add(new Bin("종이", "paper_bin.png"));
		bins.add(new Bin("일반", "regular_bin.png"));
		
		String message = "잘하고 있어요! 이제 분리배출의 달인으로 한 단계 더 나아가 볼까요?\r\n"
				+ "이번 레벨에서는 새로운 아이템과 분리배출함이 추가됩니다.\r\n 새로운 아이템을 올바르게 배출해 보세요!";

		return new LevelData(2, itemTemplates, bins, message);
	}
	
	private LevelData createDataLevel3() {
		// 레벨3 데이터
		List<Item> itemTemplates = new ArrayList<>();

		itemTemplates.add(new Item("빈 페트병", "페트", "petbottle_none_cola.png"));
		
		itemTemplates.add(new Item("유리병", "유리", "glassbottle_sink_none.png"));
		
		itemTemplates.add(new Item("지퍼팩", "비닐", "vinyl.png"));
		itemTemplates.add(new Item("비닐봉지", "비닐", "vinyl_2.png"));
		
		itemTemplates.add(new Item("유리컵", "유리", "glassbottle_cup.png"));
		
		itemTemplates.add(new ComplexItem("라벨이 있는 페트병", "라벨이 있는 페트", "petbottle_cola_label.png", new Item("페트병", "페트", "petbottle_none_cola.png"), new Item("라벨", "비닐", "lable_vinyl.png"), "커터"));
		itemTemplates.add(new ComplexItem("음료수가 있는 유리병", "음료수가 있는 유리", "glassbottle_sink.png", new Item("빈 유리병", "유리", "glassbottle_sink_none.png"), "싱크대"));
		itemTemplates.add(new ComplexItem("음료수가 있는 페트병", "음료수가 있는 페트", "petbottle_sink.png", new Item("페트병", "페트", "petbottle_sink_none.png"), "싱크대"));
		
		List<Bin> bins = new ArrayList<>();
		bins.add(new Bin("페트", "pet_bin.png"));
		bins.add(new Bin("유리", "glass_bin.png"));
		bins.add(new Bin("비닐", "vinyl_bin.png"));
		
		List<Tool> tools = new ArrayList<>();
		tools.add(new Tool("커터", "커터.png"));
		tools.add(new Tool("싱크대", "싱크대.png"));
		
		String message = "당신은 이제 분리배출 마스터가 될 준비가 되었습니다!\r\n" + "적절한 도구를 이용해\r\n 더 복잡한 분리배출을 완수해야 합니다. 도전하세요!";

		return new LevelData(3, itemTemplates, bins, tools, message);
	}
}