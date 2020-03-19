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
import Entidades.Socio;
import Entidades.Ingresos;
import Entidades.Iprivados;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Cristian
 */
public class IprivadosJpaController implements Serializable {

    public IprivadosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Iprivados iprivados) {
        if (iprivados.getIngresosList() == null) {
            iprivados.setIngresosList(new ArrayList<Ingresos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Socio idSocio = iprivados.getIdSocio();
            if (idSocio != null) {
                idSocio = em.getReference(idSocio.getClass(), idSocio.getIdSocio());
                iprivados.setIdSocio(idSocio);
            }
            List<Ingresos> attachedIngresosList = new ArrayList<Ingresos>();
            for (Ingresos ingresosListIngresosToAttach : iprivados.getIngresosList()) {
                ingresosListIngresosToAttach = em.getReference(ingresosListIngresosToAttach.getClass(), ingresosListIngresosToAttach.getIdIngresos());
                attachedIngresosList.add(ingresosListIngresosToAttach);
            }
            iprivados.setIngresosList(attachedIngresosList);
            em.persist(iprivados);
            if (idSocio != null) {
                idSocio.getIprivadosList().add(iprivados);
                idSocio = em.merge(idSocio);
            }
            for (Ingresos ingresosListIngresos : iprivados.getIngresosList()) {
                ingresosListIngresos.getIprivadosList().add(iprivados);
                ingresosListIngresos = em.merge(ingresosListIngresos);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Iprivados iprivados) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Iprivados persistentIprivados = em.find(Iprivados.class, iprivados.getIdiPrivados());
            Socio idSocioOld = persistentIprivados.getIdSocio();
            Socio idSocioNew = iprivados.getIdSocio();
            List<Ingresos> ingresosListOld = persistentIprivados.getIngresosList();
            List<Ingresos> ingresosListNew = iprivados.getIngresosList();
            if (idSocioNew != null) {
                idSocioNew = em.getReference(idSocioNew.getClass(), idSocioNew.getIdSocio());
                iprivados.setIdSocio(idSocioNew);
            }
            List<Ingresos> attachedIngresosListNew = new ArrayList<Ingresos>();
            for (Ingresos ingresosListNewIngresosToAttach : ingresosListNew) {
                ingresosListNewIngresosToAttach = em.getReference(ingresosListNewIngresosToAttach.getClass(), ingresosListNewIngresosToAttach.getIdIngresos());
                attachedIngresosListNew.add(ingresosListNewIngresosToAttach);
            }
            ingresosListNew = attachedIngresosListNew;
            iprivados.setIngresosList(ingresosListNew);
            iprivados = em.merge(iprivados);
            if (idSocioOld != null && !idSocioOld.equals(idSocioNew)) {
                idSocioOld.getIprivadosList().remove(iprivados);
                idSocioOld = em.merge(idSocioOld);
            }
            if (idSocioNew != null && !idSocioNew.equals(idSocioOld)) {
                idSocioNew.getIprivadosList().add(iprivados);
                idSocioNew = em.merge(idSocioNew);
            }
            for (Ingresos ingresosListOldIngresos : ingresosListOld) {
                if (!ingresosListNew.contains(ingresosListOldIngresos)) {
                    ingresosListOldIngresos.getIprivadosList().remove(iprivados);
                    ingresosListOldIngresos = em.merge(ingresosListOldIngresos);
                }
            }
            for (Ingresos ingresosListNewIngresos : ingresosListNew) {
                if (!ingresosListOld.contains(ingresosListNewIngresos)) {
                    ingresosListNewIngresos.getIprivadosList().add(iprivados);
                    ingresosListNewIngresos = em.merge(ingresosListNewIngresos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = iprivados.getIdiPrivados();
                if (findIprivados(id) == null) {
                    throw new NonexistentEntityException("The iprivados with id " + id + " no longer exists.");
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
            Iprivados iprivados;
            try {
                iprivados = em.getReference(Iprivados.class, id);
                iprivados.getIdiPrivados();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The iprivados with id " + id + " no longer exists.", enfe);
            }
            Socio idSocio = iprivados.getIdSocio();
            if (idSocio != null) {
                idSocio.getIprivadosList().remove(iprivados);
                idSocio = em.merge(idSocio);
            }
            List<Ingresos> ingresosList = iprivados.getIngresosList();
            for (Ingresos ingresosListIngresos : ingresosList) {
                ingresosListIngresos.getIprivadosList().remove(iprivados);
                ingresosListIngresos = em.merge(ingresosListIngresos);
            }
            em.remove(iprivados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Iprivados> findIprivadosEntities() {
        return findIprivadosEntities(true, -1, -1);
    }

    public List<Iprivados> findIprivadosEntities(int maxResults, int firstResult) {
        return findIprivadosEntities(false, maxResults, firstResult);
    }

    private List<Iprivados> findIprivadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Iprivados.class));
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

    public Iprivados findIprivados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Iprivados.class, id);
        } finally {
            em.close();
        }
    }

    public int getIprivadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Iprivados> rt = cq.from(Iprivados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
