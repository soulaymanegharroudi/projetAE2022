package be.vinci.pae.business.domain.dtos;

import be.vinci.pae.business.domain.interfaces.MemberDTO;

public class MemberDTOImpl implements MemberDTO {

  private int idMember;
  private String password;
  private String username;
  private String lastName;
  private String firstName;
  private String callNumber;
  private String reasonForConnRefusal;
  private String state;
  private boolean isAdmin;
  private int countObjectNotCollected;
  private int countObjectGiven;
  private int countObjectGot = 0;

  /**
   * Empty constructor.
   */
  public MemberDTOImpl() {

  }

  /**
   * create new MemberDTOImpl.
   *
   * @param idMember             id's member
   * @param password             hashed password
   * @param username             unique username
   * @param lastName             last name
   * @param firstName            first name
   * @param callNumber           call number
   * @param reasonForConnRefusal reason why this member cannot connect to the website
   * @param state                state where the member lives
   * @param isAdmin              is the member admin or not
   */
  public MemberDTOImpl(int idMember, String password, String username, String lastName,
      String firstName, String callNumber, String reasonForConnRefusal, String state,
      boolean isAdmin) {
    this.idMember = idMember;
    this.password = password;
    this.username = username;
    this.lastName = lastName;
    this.firstName = firstName;
    this.callNumber = callNumber;
    this.reasonForConnRefusal = reasonForConnRefusal;
    this.state = state;
    this.isAdmin = isAdmin;

  }

  @Override
  public int getIdMember() {
    return idMember;
  }

  @Override
  public void setIdMember(int idMember) {
    this.idMember = idMember;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String getFirstName() {
    return firstName;
  }

  @Override
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public String getCallNumber() {
    return callNumber;
  }

  @Override
  public void setCallNumber(String callNumber) {
    this.callNumber = callNumber;
  }

  @Override
  public String getReasonForConnRefusal() {
    return reasonForConnRefusal;
  }

  @Override
  public void setReasonForConnRefusal(String reasonForConnRefusal) {
    this.reasonForConnRefusal = reasonForConnRefusal;
  }

  @Override
  public String getState() {
    return state;
  }

  @Override
  public void setState(String state) {
    this.state = state;
  }

  @Override
  public boolean isAdmin() {
    return isAdmin;
  }

  @Override
  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  @Override
  public int getCountObjectNotCollected() {
    return countObjectNotCollected;
  }

  @Override
  public void setCountObjectNotCollected(int countObjectNotCollected) {
    this.countObjectNotCollected = countObjectNotCollected;
  }

  @Override
  public int getCountObjectGiven() {
    return countObjectGiven;
  }

  @Override
  public void setCountObjectGiven(int countObjectGiven) {
    this.countObjectGiven = countObjectGiven;
  }

  @Override
  public int getCountObjectGot() {
    return countObjectGot;
  }

  @Override
  public void setCountObjectGot(int countObjectGot) {
    this.countObjectGot = countObjectGot;
  }

  @Override
  public String toString() {
    return "Member{"
        + "id_member="
        + idMember
        + ", username='"
        + username
        + '\''
        + ", last_name='"
        + lastName
        + '\''
        + ", first_name='"
        + firstName
        + '\''
        + '}';
  }

}
