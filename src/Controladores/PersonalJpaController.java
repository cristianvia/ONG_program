/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Persona;
import Entidades.Sedecentral;
import java.util.ArrayList;
import java.util.List;
import Entidades.Delegacion;
import Entidades.Personal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Cristian
 */
public class PersonalJpaController implements Serializable {

    public PersonalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Personal personal) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (personal.getSedecentralList() == null) {
            personal.setSedecentralList(new ArrayList<Sedecentral>());
        }
        if (personal.getDelegacionList() == null) {
            personal.setDelegacionList(new ArrayList<Delegacion>());
        }
        List<String> illegalOrphanMessages = null;
        Persona personaOrphanCheck = personal.getPersona();
        if (personaOrphanCheck != null) {
            Personal oldPersonalOfPersona = personaOrphanCheck.getPersonal();
            if (oldPersonalOfPersona != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Persona " + personaOrphanCheck + " already has an item of type Personal whose persona column cannot be null. Please make another selection for the persona field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona = personal.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getIdpersona());
                personal.setPersona(persona);
            }
            List<Sedecentral> attachedSedecentralList = new ArrayList<Sedecentral>();
            for (Sedecentral sedecentralListSedecentralToAttach : personal.getSedecentralList()) {
                sedecentralListSedecentralToAttach = em.getReference(sedecentralListSedecentralToAttach.getClass(), sedecentralListSedecentralToAttach.getIdSedeCentral());
                attachedSedecentralList.add(sedecentralListSedecentralToAttach);
            }
            personal.setSedecentralList(attachedSedecentralList);
            List<Delegacion> attachedDelegacionList = new ArrayList<Delegacion>();
            for (Delegacion delegacionListDelegacionToAttach : personal.getDelegacionList()) {
                delegacionListDelegacionToAttach = em.getReference(delegacionListDelegacionToAttach.getClass(), delegacionListDelegacionToAttach.getIddelegacion());
                attachedDelegacionList.add(delegacionListDelegacionToAttach);
            }
            personal.setDelegacionList(attachedDelegacionList);
            em.persist(personal);
            if (persona != null) {
                persona.setPersonal(personal);
                persona = em.merge(persona);
            }
            for (Sedecentral sedecentralListSedecentral : personal.getSedecentralList()) {
                sedecentralListSedecentral.getPersonalList().add(personal);
                sedecentralListSedecentral = em.merge(sedecentralListSedecentral);
            }
            for (Delegacion delegacionListDelegacion : personal.getDelegacionList()) {
                delegacionListDelegacion.getPersonalList().add(personal);
                delegacionListDelegacion = em.merge(delegacionListDelegacion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersonal(personal.getIdPersonal()) != null) {
                throw new PreexistingEntityException("Personal " + personal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Personal personal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personal persistentPersonal = em.find(Personal.class, personal.getIdPersonal());
            Persona personaOld = persistentPersonal.getPersona();
            Persona personaNew = personal.getPersona();
            List<Sedecentral> sedecentralListOld = persistentPersonal.getSedecentralList();
            List<Sedecentral> sedecentralListNew = personal.getSedecentralList();
            List<Delegacion> delegacionListOld = persistentPersonal.getDelegacionList();
            List<Delegacion> delegacionListNew = personal.getDelegacionList();
            List<String> illegalOrphanMessages = null;
            if (personaNew != null && !personaNew.equals(personaOld)) {
                Personal oldPersonalOfPersona = personaNew.getPersonal();
                if (oldPersonalOfPersona != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Persona " + personaNew + " already has an item of type Personal whose persona column cannot be null. Please make another selection for the persona field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getIdpersona());
                personal.setPersona(personaNew);
            }
            List<Sedecentral> attachedSedecentralListNew = new ArrayList<Sedecentral>();
            for (Sedecentral sedecentralListNewSedecentralToAttach : sedecentralListNew) {
                sedecentralListNewSedecentralToAttach = em.getReference(sedecentralListNewSedecentralToAttach.getClass(), sedecentralListNewSedecentralToAttach.getIdSedeCentral());
                attachedSedecentralListNew.add(sedecentralListNewSedecentralToAttach);
            }
            sedecentralListNew = attachedSedecentralListNew;
            personal.setSedecentralList(sedecentralListNew);
            List<Delegacion> attachedDelegacionListNew = new ArrayList<Delegacion>();
            for (Delegacion delegacionListNewDelegacionToAttach : delegacionListNew) {
                delegacionListNewDelegacionToAttach = em.getReference(delegacionListNewDelegacionToAttach.getClass(), delegacionListNewDelegacionToAttach.getIddelegacion());
                attachedDelegacionListNew.add(delegacionListNewDelegacionToAttach);
            }
            delegacionListNew = attachedDelegacionListNew;
            personal.setDelegacionList(delegacionListNew);
            personal = em.merge(personal);
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.setPersonal(null);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.setPersonal(personal);
                personaNew = em.merge(personaNew);
            }
            for (Sedecentral sedecentralListOldSedecentral : sedecentralListOld) {
                if (!sedecentralListNew.contains(sedecentralListOldSedecentral)) {
                    sedecentralListOldSedecentral.getPersonalList().remove(personal);
                    sedecentralListOldSedecentral = em.merge(sedecentralListOldSedecentral);
                }
            }
            for (Sedecentral sedecentralListNewSedecentral : sedecentralListNew) {
                if (!sedecentralListOld.contains(sedecentralListNewSedecentral)) {
                    sedecentralListNewSedecentral.getPersonalList().add(personal);
                    sedecentralListNewSedecentral = em.merge(sedecentralListNewSedecentral);
                }
            }
            for (Delegacion delegacionListOldDelegacion : delegacionListOld) {
                if (!delegacionListNew.contains(delegacionListOldDelegacion)) {
                    delegacionListOldDelegacion.getPersonalList().remove(personal);
                    delegacionListOldDelegacion = em.merge(delegacionListOldDelegacion);
                }
            }
            for (Delegacion delegacionListNewDelegacion : delegacionListNew) {
                if (!delegacionListOld.contains(delegacionListNewDelegacion)) {
                    delegacionListNewDelegacion.getPersonalList().add(personal);
                    delegacionListNewDelegacion = em.merge(delegacionListNewDelegacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = personal.getIdPersonal();
                if (findPersonal(id) == null) {
                    throw new NonexistentEntityException("The personal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personal personal;
            try {
                personal = em.getReference(Personal.class, id);
                personal.getIdPersonal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personal with id " + id + " no longer exists.", enfe);
            }
            Persona persona = personal.getPersona();
            if (persona != null) {
                persona.setPersonal(null);
                persona = em.merge(persona);
            }
            List<Sedecentral> sedecentralList = personal.getSedecentralList();
            for (Sedecentral sedecentralListSedecentral : sedecentralList) {
                sedecentralListSedecentral.getPersonalList().remove(personal);
                sedecentralListSedecentral = em.merge(sedecentralListSedecentral);
            }
            List<Delegacion> delegacionList = personal.getDelegacionList();
            for (Delegacion delegacionListDelegacion : delegacionList) {
                delegacionListDelegacion.getPersonalList().remove(personal);
                delegacionListDelegacion = em.merge(delegacionListDelegacion);
            }
            em.remove(personal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Personal> findPersonalEntities() {
        return findPersonalEntities(true, -1, -1);
    }

    public List<Personal> findPersonalEntities(int maxResults, int firstResult) {
        return findPersonalEntities(false, maxResults, firstResult);
    }

    private List<Personal> findPersonalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Personal.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Personal findPersonal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Personal.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Personal> rt = cq.from(Personal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
