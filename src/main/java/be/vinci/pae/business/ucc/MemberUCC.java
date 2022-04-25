package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.AddressDTO;
import be.vinci.pae.business.domain.interfacesdto.MemberDTO;
import java.util.ArrayList;

public interface MemberUCC {


  String getState(String username);


  MemberDTO getOne(int id);

  /**
   * Permit to a disconnected user to log in.
   *
   * @param username username of the member
   * @param password non hashed password
   */
  MemberDTO login(String username, String password);

  MemberDTO confirmRegistration(String username, boolean isAdmin);

  MemberDTO updateMember(MemberDTO oldMember, MemberDTO newMember);

  ArrayList<MemberDTO> listUsersByState(String state);


  ArrayList<MemberDTO> listDeniedUsers();

  ArrayList<MemberDTO> listPendingUsers();

  MemberDTO denyRegistration(String username, String reason_for_conn_refusal);

  MemberDTO register(MemberDTO member, AddressDTO address);

  Object getOneByUsername(String username);
}
