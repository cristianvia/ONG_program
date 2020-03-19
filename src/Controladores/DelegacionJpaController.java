/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Delegacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Personal;
import java.util.ArrayList;
import java.util.List;
import Entidades.Ong;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Cristian
 */
public class DelegacionJpaController implements Serializable {

    public DelegacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Delegacion delegacion) {
        if (delegacion.getPersonalList() == null) {
            delegacion.setPersonalList(new ArrayList<Personal>());
        }
        if (delegacion.getOngList() == null) {
            delegacion.setOngList(new ArrayList<Ong>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Personal> attachedPersonalList = new ArrayList<Personal>();
            for (Personal personalListPersonalToAttach : delegacion.getPersonalList()) {
                personalListPersonalToAttach = em.getReference(personalListPersonalToAttach.getClass(), personalListPersonalToAttach.getIdPersonal());
                attachedPersonalList.add(personalListPersonalToAttach);
            }
            delegacion.setPersonalList(attachedPersonalList);
            List<Ong> attachedOngList = new ArrayList<Ong>();
            for (Ong ongListOngToAttach : delegacion.getOngList()) {
                ongListOngToAttach = em.getReference(ongListOngToAttach.getClass(), ongListOngToAttach.getOngPK());
                attachedOngList.add(ongListOngToAttach);
            }
            delegacion.setOngList(attachedOngList);
            em.persist(delegacion);
            for (Personal personalListPersonal : delegacion.getPersonalList()) {
                personalListPersonal.getDelegacionList().add(delegacion);
                personalListPersonal = em.merge(personalListPersonal);
            }
            for (Ong ongListOng : delegacion.getOngList()) {
                Delegacion oldDelegacionOfOngListOng = ongListOng.getDelegacion();
                ongListOng.setDelegacion(delegacion);
                ongListOng = em.merge(ongListOng);
                if (oldDelegacionOfOngListOng != null) {
                    oldDelegacionOfOngListOng.getOngList().remove(ongListOng);
                    oldDelegacionOfOngListOng = em.merge(oldDelegacionOfOngListOng);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Delegacion delegacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Delegacion persistentDelegacion = em.find(Delegacion.class, delegacion.getIddelegacion());
            List<Personal> personalListOld = persistentDelegacion.getPersonalList();
            List<Personal> personalListNew = delegacion.getPersonalList();
            List<Ong> ongListOld = persistentDelegacion.getOngList();
            List<Ong> ongListNew = delegacion.getOngList();
            List<String> illegalOrphanMessages = null;
            for (Ong ongListOldOng : ongListOld) {
                if (!ongListNew.contains(ongListOldOng)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ong " + ongListOldOng + " since its delegacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Personal> attachedPersonalListNew = new ArrayList<Personal>();
            for (Personal personalListNewPersonalToAttach : personalListNew) {
                personalListNewPersonalToAttach = em.getReference(personalListNewPersonalToAttach.getClass(), personalListNewPersonalToAttach.getIdPersonal());
                attachedPersonalListNew.add(personalListNewPersonalToAttach);
            }
            personalListNew = attachedPersonalListNew;
            delegacion.setPersonalList(personalListNew);
            List<Ong> attachedOngListNew = new ArrayList<Ong>();
            for (Ong ongListNewOngToAttach : ongListNew) {
                ongListNewOngToAttach = em.getReference(ongListNewOngToAttach.getClass(), ongListNewOngToAttach.getOngPK());
                attachedOngListNew.add(ongListNewOngToAttach);
            }
            ongListNew = attachedOngListNew;
            delegacion.setOngList(ongListNew);
            delegacion = em.merge(delegacion);
            for (Personal personalListOldPersonal : personalListOld) {
                if (!personalListNew.contains(personalListOldPersonal)) {
                    personalListOldPersonal.getDelegacionList().remove(delegacion);
                    personalListOldPersonal = em.merge(personalListOldPersonal);
                }
            }
            for (Personal personalListNewPersonal : personalListNew) {
                if (!personalListOld.contains(personalListNewPersonal)) {
                    personalListNewPersonal.getDelegacionList().add(delegacion);
                    personalListNewPersonal = em.merge(personalListNewPersonal);
                }
            }
            for (Ong ongListNewOng : ongListNew) {
                if (!ongListOld.contains(ongListNewOng)) {
                    Delegacion oldDelegacionOfOngListNewOng = ongListNewOng.getDelegacion();
                    ongListNewOng.setDelegacion(delegacion);
                    ongListNewOng = em.merge(ongListNewOng);
                    if (oldDelegacionOfOngListNewOng != null && !oldDelegacionOfOngListNewOng.equals(delegacion)) {
                        oldDelegacionOfOngListNewOng.getOngList().remove(ongListNewOng);
                        oldDelegacionOfOngListNewOng = em.merge(oldDelegacionOfOngListNewOng);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = delegacion.getIddelegacion();
                if (findDelegacion(id) == null) {
                    throw new NonexistentEntityException("The delegacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Delegacion delegacion;
            try {
                delegacion = em.getReference(Delegacion.class, id);
                delegacion.getIddelegacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The delegacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ong> ongListOrphanCheck = delegacion.getOngList();
            for (Ong ongListOrphanCheckOng : ongListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Delegacion (" + delegacion + ") cannot be destroyed since the Ong " + ongListOrphanCheckOng + " in its ongList field has a non-nullable delegacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Personal> personalList = delegacion.getPersonalList();
            for (Personal personalListPersonal : personalList) {
                personalListPersonal.getDelegacionList().remove(delegacion);
                personalListPersonal = em.merge(personalListPersonal);
            }
            em.remove(delegacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Delegacion> findDelegacionEntities() {
        return findDelegacionEntities(true, -1, -1);
    }

    public List<Delegacion> findDelegacionEntities(int maxResults, int firstResult) {
        return findDelegacionEntities(false, maxResults, firstResult);
    }

    private List<Delegacion> findDelegacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Delegacion.class));
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

    public Delegacion findDelegacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Delegacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDelegacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Delegacion> rt = cq.from(Delegacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
