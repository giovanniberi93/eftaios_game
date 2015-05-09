package nameGame;


public enum CharacterName {
	CAPTAIN("The captain","Ennio Maria Dominoni",SideName.HUMAN),PILOT("The pilot","Julia Niguloti",SideName.HUMAN),
	PSYCHOLOGIST("The psychologist","Silvano Porpora",SideName.HUMAN),SOLDIER("The soldier","Tuccio Brendon a.k.a. “Piri”",SideName.HUMAN),
	FIRSTALIEN("The first alien","Piero Ceccarella",SideName.ALIEN),SECONDALIEN("The second Alien","Vittorio Martana",SideName.ALIEN),
	THIRDALIEN("The third alien","Maria Galbani",SideName.ALIEN),FOURTHALIEN("The fourth alien","Paolo Landon",SideName.ALIEN);
	
	private String ruleName;
	private String personalName;
	private SideName side;
	
	public String getRuleName() {
		return ruleName;
	}

	public String getPersonalName() {
		return personalName;
	}
    
    public SideName getSide() {
		return side;
	}

	private CharacterName(String rule,String personalName,SideName side) {
         this.ruleName = rule;
         this.personalName = personalName;
         this.side = side;
    }
     
}
