public interface Action{
	public String title = "untitled action";
	public ActionTargetType[] targets = new ActionTargetType[0];
	public void action(TestConsole testConsole, EmployeeBook employeeBook);
}