package be.vinci.pae.dal;

import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import be.vinci.pae.dal.interfaces.MemberDao;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberDaoImpl implements MemberDao {

  @Inject
  private DomainFactory domainFactory;
  @Inject
  private DalServices services;

  public MemberDaoImpl() {

  }

  /**
   * Get a member according to the username given in parameter and execute a query given by
   * DalServices class.
   *
   * @param username member's username that you want get
   */
  public MemberDTO getMember(String username) {
    MemberDTO member = null;
    PreparedStatement query = null;
    try {
      query = services.getPreparedStatement(
          "SELECT id_member, password, username,"
              + " last_name, first_name, call_number, isadmin, reason_for_conn_refusal,"
              + " state, count_object_not_collected, count_object_given, count_object_got"
              + " FROM pae.members "
              + "WHERE username = ?");
      query.setString(1, username);
      member = getMemberFromDataBase(query);

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        assert query != null;
        query.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
    return member;
  }

  /**
   * Get a member according to the username given in parameter and execute a query given by
   * DalServices class.
   *
   * @param id member's id that you want get
   */
  @Override
  public MemberDTO getMember(int id) {
    MemberDTO member = null;
    PreparedStatement query = null;
    try {
      query = services.getPreparedStatement(
          "SELECT id_member, password, username,"
              + " last_name, first_name, call_number, isadmin, reason_for_conn_refusal,"
              + " state, count_object_not_collected, count_object_given, count_object_got"
              + " FROM pae.members "
              + "WHERE id_member = ?");
      query.setInt(1, id);
      member = getMemberFromDataBase(query);

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        assert query != null;
        query.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
    return member;
  }


  @Override
  public ArrayList<MemberDTO> listPendingUsers() {
    ArrayList<MemberDTO> list = new ArrayList<>();
    PreparedStatement query;
    try {
      query = services.getPreparedStatement(
          "SELECT id_member FROM pae.members WHERE state='valid'");

      ResultSet resultSet = query.executeQuery();
      while (resultSet.next()) {
        list.add(createMemberInstance(resultSet));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;

  }


  public MemberDTO confirmInscription(String username, boolean isAdmin) {
    MemberDTO member = null;

    try (

        PreparedStatement query = services.getPreparedStatement(
            "UPDATE pae.members SET state='confirmed', isAdmin =? WHERE username=?")

    ) {
      query.setBoolean(1, isAdmin);

      query.setString(2, username);
      member = getMemberFromDataBase(query);

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return member;
  }

  public MemberDTO createMemberInstance(ResultSet resultSetMember) throws SQLException {

    MemberDTO member = domainFactory.getMember();

    member.setIdMember(resultSetMember.getInt(1));
    member.setPassword(resultSetMember.getString(2));
    member.setUsername(resultSetMember.getString(3));
    member.setLastName(resultSetMember.getString(4));
    member.setFirstName(resultSetMember.getString(5));
    member.setCallNumber(resultSetMember.getString(6));
    member.setAdmin(resultSetMember.getBoolean(7));
    member.setReasonForConnRefusal(resultSetMember.getString(8));
    member.setState(resultSetMember.getString(9));
    member.setCountObjectNotCollected(resultSetMember.getInt(10));
    member.setCountObjectGiven(resultSetMember.getInt(11));
    member.setCountObjectGot(resultSetMember.getInt(12));
    resultSetMember.close();
    return member;
  }


  /**
   * Avoid duplicate code if we want to get a user from the dataBase.
   *
   * @param query query to execute
   * @return return the member got in the database
   */
  private MemberDTO getMemberFromDataBase(PreparedStatement query) throws SQLException {
    MemberDTO member = domainFactory.getMember();
    ResultSet resultSetMember = query.executeQuery();

    if (!resultSetMember.next()) {
      throw new WebApplicationException("Username not found");
    }
    member.setIdMember(resultSetMember.getInt(1));
    member.setPassword(resultSetMember.getString(2));
    member.setUsername(resultSetMember.getString(3));
    member.setLastName(resultSetMember.getString(4));
    member.setFirstName(resultSetMember.getString(5));
    member.setCallNumber(resultSetMember.getString(6));
    member.setAdmin(resultSetMember.getBoolean(7));
    member.setReasonForConnRefusal(resultSetMember.getString(8));
    member.setState(resultSetMember.getString(9));
    member.setCountObjectNotCollected(resultSetMember.getInt(10));
    member.setCountObjectGiven(resultSetMember.getInt(11));
    member.setCountObjectGot(resultSetMember.getInt(12));
    resultSetMember.close();
    return member;
  }

}
