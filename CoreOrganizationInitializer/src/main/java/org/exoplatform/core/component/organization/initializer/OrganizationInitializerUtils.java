package org.exoplatform.core.component.organization.initializer;

import javax.jcr.Node;
import javax.jcr.Session;

import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.organization.Group;
import org.exoplatform.services.organization.Membership;
import org.exoplatform.services.organization.User;
import org.exoplatform.services.organization.UserProfile;

public class OrganizationInitializerUtils {

  final public static String ORGANIZATION_INITIALIZATIONS = "organizationInitializers".intern();
  final public static String USERS_FOLDER = "users".intern();
  final public static String GROUPS_FOLDER = "groups".intern();
  final public static String MEMBERSHIPS_FOLDER = "memberships".intern();
  final public static String PROFILES_FOLDER = "profiles".intern();

  public static String REPOSITORY = "repository".intern();
  public static String WORKSPACE = "collaboration".intern();
  public static String HOME_PATH = "/".intern();

  public static void init(Session session) throws Exception {
    Node homePathNode = null;
    try {
      homePathNode = (Node) session.getItem(HOME_PATH);
    } catch (Exception e) {}
    if (homePathNode == null) {
      homePathNode = createFolder(session.getRootNode(), HOME_PATH);
    }
    Node organizationInitializersHomePathNode = null;
    if (!homePathNode.hasNode(ORGANIZATION_INITIALIZATIONS)) {
      organizationInitializersHomePathNode = createFolder(homePathNode, ORGANIZATION_INITIALIZATIONS);
    } else {
      organizationInitializersHomePathNode = homePathNode.getNode(ORGANIZATION_INITIALIZATIONS);
    }
    if (!organizationInitializersHomePathNode.hasNode(USERS_FOLDER)) {
      createFolder(organizationInitializersHomePathNode, USERS_FOLDER);
    }
    if (!organizationInitializersHomePathNode.hasNode(GROUPS_FOLDER)) {
      createFolder(organizationInitializersHomePathNode, GROUPS_FOLDER);
    }
    if (!organizationInitializersHomePathNode.hasNode(MEMBERSHIPS_FOLDER)) {
      createFolder(organizationInitializersHomePathNode, MEMBERSHIPS_FOLDER);
    }
    if (!organizationInitializersHomePathNode.hasNode(PROFILES_FOLDER)) {
      createFolder(organizationInitializersHomePathNode, PROFILES_FOLDER);
    }
    session.save();
  }

  private static Node getUsersFolder(Session session) throws Exception {
    Node organizationInitializersHomePathNode = null;
    try {
      Node homePathNode = (Node) session.getItem(HOME_PATH);
      organizationInitializersHomePathNode = homePathNode.getNode(ORGANIZATION_INITIALIZATIONS);
    } catch (Exception e) {
      init(session);
      Node homePathNode = (Node) session.getItem(HOME_PATH);
      organizationInitializersHomePathNode = homePathNode.getNode(ORGANIZATION_INITIALIZATIONS);
    }
    return organizationInitializersHomePathNode.getNode(USERS_FOLDER);
  }

  private static Node getGroupsFolder(Session session) throws Exception {
    Node organizationInitializersHomePathNode = null;
    try {
      Node homePathNode = (Node) session.getItem(HOME_PATH);
      organizationInitializersHomePathNode = homePathNode.getNode(ORGANIZATION_INITIALIZATIONS);
    } catch (Exception e) {
      init(session);
      Node homePathNode = (Node) session.getItem(HOME_PATH);
      organizationInitializersHomePathNode = homePathNode.getNode(ORGANIZATION_INITIALIZATIONS);
    }
    return organizationInitializersHomePathNode.getNode(GROUPS_FOLDER);
  }

  private static Node getMembershipsFolder(Session session) throws Exception {
    Node organizationInitializersHomePathNode = null;
    try {
      Node homePathNode = (Node) session.getItem(HOME_PATH);
      organizationInitializersHomePathNode = homePathNode.getNode(ORGANIZATION_INITIALIZATIONS);
    } catch (Exception e) {
      init(session);
      Node homePathNode = (Node) session.getItem(HOME_PATH);
      organizationInitializersHomePathNode = homePathNode.getNode(ORGANIZATION_INITIALIZATIONS);
    }
    return organizationInitializersHomePathNode.getNode(MEMBERSHIPS_FOLDER);
  }

  private static Node getProfilesFolder(Session session) throws Exception {
    Node organizationInitializersHomePathNode = null;
    try {
      Node homePathNode = (Node) session.getItem(HOME_PATH);
      organizationInitializersHomePathNode = homePathNode.getNode(ORGANIZATION_INITIALIZATIONS);
    } catch (Exception e) {
      init(session);
      Node homePathNode = (Node) session.getItem(HOME_PATH);
      organizationInitializersHomePathNode = homePathNode.getNode(ORGANIZATION_INITIALIZATIONS);
    }
    return organizationInitializersHomePathNode.getNode(PROFILES_FOLDER);
  }

  public static boolean hasUserFolder(RepositoryService repositoryService, User user) throws Exception {
    Session session = repositoryService.getRepository(REPOSITORY).getSystemSession(WORKSPACE);
    return getUsersFolder(session).hasNode(user.getUserName());
  }

  public static boolean hasProfileFolder(RepositoryService repositoryService, UserProfile userProfile) throws Exception {
    Session session = repositoryService.getRepository(REPOSITORY).getSystemSession(WORKSPACE);
    return getProfilesFolder(session).hasNode(userProfile.getUserName());
  }

  public static boolean hasMembershipFolder(RepositoryService repositoryService, Membership membership) throws Exception {
    Session session = repositoryService.getRepository(REPOSITORY).getSystemSession(WORKSPACE);
    return getMembershipsFolder(session).hasNode(membership.getGroupId().replace("/", "") + membership.getMembershipType().toString().replace("*", "_") + membership.getUserName());
  }

  public static boolean hasGroupFolder(RepositoryService repositoryService, Group group) throws Exception {
    Session session = repositoryService.getRepository(REPOSITORY).getSystemSession(WORKSPACE);
    return getGroupsFolder(session).hasNode(group.getId().replace("/", "").trim());
  }

  public static void createUserFolder(RepositoryService repositoryService, User user) throws Exception {
    Session session = repositoryService.getRepository(REPOSITORY).getSystemSession(WORKSPACE);
    createFolder(getUsersFolder(session), user.getUserName());
    session.save();
  }

  public static void createProfileFolder(RepositoryService repositoryService, UserProfile userProfile) throws Exception {
    Session session = repositoryService.getRepository(REPOSITORY).getSystemSession(WORKSPACE);
    createFolder(getProfilesFolder(session), userProfile.getUserName());
    session.save();
  }

  public static void createMembershipFolder(RepositoryService repositoryService, Membership membership) throws Exception {
    Session session = repositoryService.getRepository(REPOSITORY).getSystemSession(WORKSPACE);
    createFolder(getMembershipsFolder(session), membership.getGroupId().replace("/", "") + membership.getMembershipType().toString().replace("*", "_") + membership.getUserName());
    session.save();
  }

  public static void createGroupFolder(RepositoryService repositoryService, Group group) throws Exception {
    Session session = repositoryService.getRepository(REPOSITORY).getSystemSession(WORKSPACE);
    createFolder(getGroupsFolder(session), group.getId().replace("/", "").trim());
    session.save();
  }

  public static void deleteUserFolder(RepositoryService repositoryService, User user) throws Exception {
    Session session = repositoryService.getRepository(REPOSITORY).getSystemSession(WORKSPACE);
    getUsersFolder(session).getNode(user.getUserName()).remove();
    session.save();
  }

  public static void deleteProfileFolder(RepositoryService repositoryService, UserProfile userProfile) throws Exception {
    Session session = repositoryService.getRepository(REPOSITORY).getSystemSession(WORKSPACE);
    getProfilesFolder(session).getNode(userProfile.getUserName()).remove();
    session.save();
  }

  public static void deleteMembershipFolder(RepositoryService repositoryService, Membership membership) throws Exception {
    Session session = repositoryService.getRepository(REPOSITORY).getSystemSession(WORKSPACE);
    getMembershipsFolder(session).getNode(membership.getGroupId().replace("/", "") + membership.getMembershipType().toString().replace("*", "_") + membership.getUserName()).remove();
    session.save();
  }

  public static void deleteGroupFolder(RepositoryService repositoryService, Group group) throws Exception {
    Session session = repositoryService.getRepository(REPOSITORY).getSystemSession(WORKSPACE);
    getGroupsFolder(session).getNode(group.getId().replace("/", "")).remove();
    session.save();
  }

  private static Node createFolder(Node parentNode, String name) throws Exception {
    parentNode.addNode(name, "nt:folder");
    parentNode.getSession().save();
    return parentNode.getNode(name);
  }
}