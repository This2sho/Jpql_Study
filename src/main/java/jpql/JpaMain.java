package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setAge(0);
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setAge(0);
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setAge(0);
            member3.setTeam(teamB);
            em.persist(member3);


            em.flush();
            em.clear();

//            String query = "select distinct m from Member m join fetch m.team";
//            List<Member> result = em.createQuery(query, Member.class)
//                    .getResultList();
//            for (Member member : result) {
//                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
//                //회원1, 팀A(SQL)
//                //회원2, 팀A(1차캐시)
//                //회원3, 팀B(SQL)
//
//                //회원 100명 -> N + 1
//            }
//            String query = "select t from Team t";
//            List<Team> result = em.createQuery(query, Team.class)
//                    .setFirstResult(0)
//                    .setMaxResults(2)
//                    .getResultList();
//            for (Team team : result) {
//                System.out.println("team.getName() + = " + team.getName() + " |members = " +team.getMembers());
//                for (Member member : team.getMembers()) {
//                    System.out.println("-> member = " + member);
//                }
//            }
//            String query = "select m from Member m where m = :member";

//            Member findMember = em.createQuery(query, Member.class)
//                    .setParameter("member", member1)
//                    .getSingleResult();
//            System.out.println("findMember = " + findMember);

//            List<Member> result = em.createNamedQuery("Member.findByUsername", Member.class)
//                    .setParameter("username", "회원1")
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member = " + member);
//            }

            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();
            em.clear();
            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember.getAge() = " + findMember.getAge());
            System.out.println("resultCount = " + resultCount);

            System.out.println("member1.getAge() = " + member1.getAge());
            System.out.println("member2.getAge() = " + member2.getAge());
            System.out.println("member3.getAge() = " + member3.getAge());

            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
