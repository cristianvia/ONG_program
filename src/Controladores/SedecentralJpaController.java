/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Personal;
import java.util.ArrayList;
import java.util.List;
import Entidades.Ong;
import Entidades.Sedecentral;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Cristian
 */
public class SedecentralJpaController implements Serializable {

    public SedecentralJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sedecentral sedecentral) {
        if (sedecentral.getPersonalList() == null) {
            sedecentral.setPersonalList(new ArrayList<Personal>());
        }
        if (sedecentral.getOngList() == null) {
            sedecentral.setOngList(new ArrayList<Ong>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Personal> attachedPersonalList = new ArrayList<Personal>();
            for (Personal personalListPersonalToAttach : sedecentral.getPersonalList()) {
                personalListPersonalToAttach = em.getReference(personalListPersonalToAttach.getClass(), personalListPersonalToAttach.getIdPersonal());
                attachedPersonalList.add(personalListPersonalToAttach);
            }
            sedecentral.setPersonalList(attachedPersonalList);
            List<Ong> attachedOngList = new ArrayList<Ong>();
            for (Ong ongListOngToAttach : sedecentral.getOngList()) {
                ongListOngToAttach = em.getReference(ongListOngToAttach.getClass(), ongListOngToAttach.getOngPK());
                attachedOngList.add(ongListOngToAttach);
            }
            sedecentral.setOngList(attachedOngList);
            em.persist(sedecentral);
            for (Personal personalListPersonal : sedecentral.getPersonalList()) {
                personalListPersonal.getSedecentralList().add(sedecentral);
                personalListPersonal = em.merge(personalListPersonal);
            }
            for (Ong ongListOng : sedecentral.getOngList()) {
                Sedecentral oldSedeCentralidSedeCentralOfOngListOng = ongListOng.getSedeCentralidSedeCentral();
                ongListOng.setSedeCentralidSedeCentral(sedecentral);
                ongListOng = em.merge(ongListOng);
                if (oldSedeCentralidSedeCentralOfOngListOng != null) {
                    oldSedeCentralidSedeCentralOfOngListOng.getOngList().remove(ongListOng);
                    oldSedeCentralidSedeCentralOfOngListOng = em.merge(oldSedeCentralidSedeCentralOfOngListOng);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sedecentral sedecentral) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sedecentral persistentSedecentral = em.find(Sedecentral.class, sedecentral.getIdSedeCentral());
            List<Personal> personalListOld = persistentSedecentral.getPersonalList();
            List<Personal> personalListNew = sedecentral.getPersonalList();
            List<Ong> ongListOld = persistentSedecentral.getOngList();
            List<Ong> ongListNew = sedecentral.getOngList();
            List<String> illegalOrphanMessages = null;
            for (Ong ongListOldOng : ongListOld) {
                if (!ongListNew.contains(ongListOldOng)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ong " + ongListOldOng + " since its sedeCentralidSedeCentral field is not nullable.");
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
            sedecentral.setPersonalList(personalListNew);
            List<Ong> attachedOngListNew = new ArrayList<Ong>();
            for (Ong ongListNewOngToAttach : ongListNew) {
                ongListNewOngToAttach = em.getReference(ongListNewOngToAttach.getClass(), ongListNewOngToAttach.getOngPK());
                attachedOngListNew.add(ongListNewOngToAttach);
            }
            ongListNew = attachedOngListNew;
            sedecentral.setOngList(ongListNew);
            sedecentral = em.merge(sedecentral);
            for (Personal personalListOldPersonal : personalListOld) {
                if (!personalListNew.contains(personalListOldPersonal)) {
                    personalListOldPersonal.getSedecentralList().remove(sedecentral);
                    personalListOldPersonal = em.merge(personalListOldPersonal);
                }
            }
            for (Personal personalListNewPersonal : personalListNew) {
                if (!personalListOld.contains(personalListNewPersonal)) {
                    personalListNewPersonal.getSedecentralList().add(sedecentral);
                    personalListNewPersonal = em.merge(personalListNewPersonal);
                }
            }
            for (Ong ongListNewOng : ongListNew) {
                if (!ongListOld.contains(ongListNewOng)) {
                    Sedecentral oldSedeCentralidSedeCentralOfOngListNewOng = ongListNewOng.getSedeCentralidSedeCentral();
                    ongListNewOng.setSedeCentralidSedeCentral(sedecentral);
                    ongListNewOng = em.merge(ongListNewOng);
                    if (oldSedeCentralidSedeCentralOfOngListNewOng != null && !oldSedeCentralidSedeCentralOfOngListNewOng.equals(sedecentral)) {
                        oldSedeCentralidSedeCentralOfOngListNewOng.getOngList().remove(ongListNewOng);
                        oldSedeCentralidSedeCentralOfOngListNewOng = em.merge(oldSedeCentralidSedeCentralOfOngListNewOng);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sedecentral.getIdSedeCentral();
                if (findSedecentral(id) == null) {
                    throw new NonexistentEntityException("The sedecentral with id " + id + " no longer exists.");
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
            Sedecentral sedecentral;
            try {
                sedecentral = em.getReference(Sedecentral.class, id);
                sedecentral.getIdSedeCentral();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sedecentral with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ong> ongListOrphanCheck = sedecentral.getOngList();
            for (Ong ongListOrphanCheckOng : ongListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sedecentral (" + sedecentral + ") cannot be destroyed since the Ong " + ongListOrphanCheckOng + " in its ongList field has a non-nullable sedeCentralidSedeCentral field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Personal> personalList = sedecentral.getPersonalList();
            for (Personal personalListPersonal : personalList) {
                personalListPersonal.getSedecentralList().remove(sedecentral);
                personalListPersonal = em.merge(personalListPersonal);
            }
            em.remove(sedecentral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sedecentral> findSedecentralEntities() {
        return findSedecentralEntities(true, -1, -1);
    }

    public List<Sedecentral> findSedecentralEntities(int maxResults, int firstResult) {
        return findSedecentralEntities(false, maxResults, firstResult);
    }

    private List<Sedecentral> findSedecentralEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sedecentral.class));
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

    public Sedecentral findSedecentral(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sedecentral.class, id);
        } finally {
            em.close();
        }
    }

    public int getSedecentralCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sedecentral> rt = cq.from(Sedecentral.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
