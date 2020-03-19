/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Ingresos;
import Entidades.Ipublicos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Cristian
 */
public class IpublicosJpaController implements Serializable {

    public IpublicosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ipublicos ipublicos) {
        if (ipublicos.getIngresosList() == null) {
            ipublicos.setIngresosList(new ArrayList<Ingresos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ingresos> attachedIngresosList = new ArrayList<Ingresos>();
            for (Ingresos ingresosListIngresosToAttach : ipublicos.getIngresosList()) {
                ingresosListIngresosToAttach = em.getReference(ingresosListIngresosToAttach.getClass(), ingresosListIngresosToAttach.getIdIngresos());
                attachedIngresosList.add(ingresosListIngresosToAttach);
            }
            ipublicos.setIngresosList(attachedIngresosList);
            em.persist(ipublicos);
            for (Ingresos ingresosListIngresos : ipublicos.getIngresosList()) {
                ingresosListIngresos.getIpublicosList().add(ipublicos);
                ingresosListIngresos = em.merge(ingresosListIngresos);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ipublicos ipublicos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ipublicos persistentIpublicos = em.find(Ipublicos.class, ipublicos.getIdIPublicos());
            List<Ingresos> ingresosListOld = persistentIpublicos.getIngresosList();
            List<Ingresos> ingresosListNew = ipublicos.getIngresosList();
            List<Ingresos> attachedIngresosListNew = new ArrayList<Ingresos>();
            for (Ingresos ingresosListNewIngresosToAttach : ingresosListNew) {
                ingresosListNewIngresosToAttach = em.getReference(ingresosListNewIngresosToAttach.getClass(), ingresosListNewIngresosToAttach.getIdIngresos());
                attachedIngresosListNew.add(ingresosListNewIngresosToAttach);
            }
            ingresosListNew = attachedIngresosListNew;
            ipublicos.setIngresosList(ingresosListNew);
            ipublicos = em.merge(ipublicos);
            for (Ingresos ingresosListOldIngresos : ingresosListOld) {
                if (!ingresosListNew.contains(ingresosListOldIngresos)) {
                    ingresosListOldIngresos.getIpublicosList().remove(ipublicos);
                    ingresosListOldIngresos = em.merge(ingresosListOldIngresos);
                }
            }
            for (Ingresos ingresosListNewIngresos : ingresosListNew) {
                if (!ingresosListOld.contains(ingresosListNewIngresos)) {
                    ingresosListNewIngresos.getIpublicosList().add(ipublicos);
                    ingresosListNewIngresos = em.merge(ingresosListNewIngresos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ipublicos.getIdIPublicos();
                if (findIpublicos(id) == null) {
                    throw new NonexistentEntityException("The ipublicos with id " + id + " no longer exists.");
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
            Ipublicos ipublicos;
            try {
                ipublicos = em.getReference(Ipublicos.class, id);
                ipublicos.getIdIPublicos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ipublicos with id " + id + " no longer exists.", enfe);
            }
            List<Ingresos> ingresosList = ipublicos.getIngresosList();
            for (Ingresos ingresosListIngresos : ingresosList) {
                ingresosListIngresos.getIpublicosList().remove(ipublicos);
                ingresosListIngresos = em.merge(ingresosListIngresos);
            }
            em.remove(ipublicos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ipublicos> findIpublicosEntities() {
        return findIpublicosEntities(true, -1, -1);
    }

    public List<Ipublicos> findIpublicosEntities(int maxResults, int firstResult) {
        return findIpublicosEntities(false, maxResults, firstResult);
    }

    private List<Ipublicos> findIpublicosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ipublicos.class));
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

    public Ipublicos findIpublicos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ipublicos.class, id);
        } finally {
            em.close();
        }
    }

    public int getIpublicosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ipublicos> rt = cq.from(Ipublicos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
