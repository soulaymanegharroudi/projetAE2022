package be.vinci.pae.main;

public class MemberDTO {

  private int idMember;
  private String password, username, lastName, firstName, callNumber, reasonForConnRefusal, state;
  private boolean isAdmin;
  private int countObjectNotCollected, countObjectGiven, countObjectGot = 0;


  public MemberDTO() {

  }


  public MemberDTO(int idMember, String password, String username, String last_name,
      String first_name, String callNumber, String reasonForConnRefusal, String state,
      boolean isAdmin) {

    this.idMember = idMember;
    this.password = password;
    this.username = username;
    this.lastName = lastName;
    this.firstName = firstName;
    this.callNumber = callNumber;
    this.reasonForConnRefusal = reasonForConnRefusal;
    if (!state.equals("pending") || !state.equals("valid") || !state.equals("denied")) {
      throw new IllegalArgumentException();
    }

    this.state = state;
    this.isAdmin = isAdmin;

  }

  public int getId_member() {
    return idMember;
  }

  public void setIdMember(int id_member) {
    this.idMember = id_member;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getLast_name() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirst_name() {
    return firstName;
  }

  public void setFirst_name(String first_name) {
    this.firstName = first_name;
  }

  public String getCall_number() {
    return callNumber;
  }

  public void setCall_number(String call_number) {
    this.callNumber = call_number;
  }

  public String getReason_for_conn_refusal() {
    return reasonForConnRefusal;
  }

  public void setReason_for_conn_refusal(String reason_for_conn_refusal) {
    this.reasonForConnRefusal = reasonForConnRefusal;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  public int getCountObjectNotCollected() {
    return countObjectNotCollected;
  }

  public void setCountObjectNotCollected(int countObjectNotCollected) {
    this.countObjectNotCollected = countObjectNotCollected;
  }

  public int getCountObjectgiven() {
    return countObjectGiven;
  }

  public void setCountObjectGiven(int countObjectGiven) {
    this.countObjectGiven = countObjectGiven;
  }

  public int getCountObjectGot() {
    return countObjectGot;
  }

  public void setCountObjectGot(int countObjectGot) {
    this.countObjectGot = countObjectGot;
  }

  @Override
  public String toString() {
    return "Member{" +
        "id_member=" + idMember +
        ", username='" + username + '\'' +
        ", last_name='" + lastName + '\'' +
        ", first_name='" + firstName + '\'' +
        '}';
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }


}
