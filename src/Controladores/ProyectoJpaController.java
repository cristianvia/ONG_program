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
import Entidades.Lineaaccion;
import Entidades.Proyecto;
import Entidades.Socio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Cristian
 */
public class ProyectoJpaController implements Serializable {

    public ProyectoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proyecto proyecto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lineaaccion lineaAccion = proyecto.getLineaAccion();
            if (lineaAccion != null) {
                lineaAccion = em.getReference(lineaAccion.getClass(), lineaAccion.getIdLa());
                proyecto.setLineaAccion(lineaAccion);
            }
            Socio socioLocal = proyecto.getSocioLocal();
            if (socioLocal != null) {
                socioLocal = em.getReference(socioLocal.getClass(), socioLocal.getIdSocio());
                proyecto.setSocioLocal(socioLocal);
            }
            em.persist(proyecto);
            if (lineaAccion != null) {
                lineaAccion.getProyectoList().add(proyecto);
                lineaAccion = em.merge(lineaAccion);
            }
            if (socioLocal != null) {
                socioLocal.getProyectoList().add(proyecto);
                socioLocal = em.merge(socioLocal);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proyecto proyecto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyecto persistentProyecto = em.find(Proyecto.class, proyecto.getIdproyecto());
            Lineaaccion lineaAccionOld = persistentProyecto.getLineaAccion();
            Lineaaccion lineaAccionNew = proyecto.getLineaAccion();
            Socio socioLocalOld = persistentProyecto.getSocioLocal();
            Socio socioLocalNew = proyecto.getSocioLocal();
            if (lineaAccionNew != null) {
                lineaAccionNew = em.getReference(lineaAccionNew.getClass(), lineaAccionNew.getIdLa());
                proyecto.setLineaAccion(lineaAccionNew);
            }
            if (socioLocalNew != null) {
                socioLocalNew = em.getReference(socioLocalNew.getClass(), socioLocalNew.getIdSocio());
                proyecto.setSocioLocal(socioLocalNew);
            }
            proyecto = em.merge(proyecto);
            if (lineaAccionOld != null && !lineaAccionOld.equals(lineaAccionNew)) {
                lineaAccionOld.getProyectoList().remove(proyecto);
                lineaAccionOld = em.merge(lineaAccionOld);
            }
            if (lineaAccionNew != null && !lineaAccionNew.equals(lineaAccionOld)) {
                lineaAccionNew.getProyectoList().add(proyecto);
                lineaAccionNew = em.merge(lineaAccionNew);
            }
            if (socioLocalOld != null && !socioLocalOld.equals(socioLocalNew)) {
                socioLocalOld.getProyectoList().remove(proyecto);
                socioLocalOld = em.merge(socioLocalOld);
            }
            if (socioLocalNew != null && !socioLocalNew.equals(socioLocalOld)) {
                socioLocalNew.getProyectoList().add(proyecto);
                socioLocalNew = em.merge(socioLocalNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proyecto.getIdproyecto();
                if (findProyecto(id) == null) {
                    throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.");
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
            Proyecto proyecto;
            try {
                proyecto = em.getReference(Proyecto.class, id);
                proyecto.getIdproyecto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.", enfe);
            }
            Lineaaccion lineaAccion = proyecto.getLineaAccion();
            if (lineaAccion != null) {
                lineaAccion.getProyectoList().remove(proyecto);
                lineaAccion = em.merge(lineaAccion);
            }
            Socio socioLocal = proyecto.getSocioLocal();
            if (socioLocal != null) {
                socioLocal.getProyectoList().remove(proyecto);
                socioLocal = em.merge(socioLocal);
            }
            em.remove(proyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proyecto> findProyectoEntities() {
        return findProyectoEntities(true, -1, -1);
    }

    public List<Proyecto> findProyectoEntities(int maxResults, int firstResult) {
        return findProyectoEntities(false, maxResults, firstResult);
    }

    private List<Proyecto> findProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proyecto.class));
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

    public Proyecto findProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proyecto> rt = cq.from(Proyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
