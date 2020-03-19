/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Lineaaccion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Proyecto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Cristian
 */
public class LineaaccionJpaController implements Serializable {

    public LineaaccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Lineaaccion lineaaccion) {
        if (lineaaccion.getProyectoList() == null) {
            lineaaccion.setProyectoList(new ArrayList<Proyecto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Proyecto> attachedProyectoList = new ArrayList<Proyecto>();
            for (Proyecto proyectoListProyectoToAttach : lineaaccion.getProyectoList()) {
                proyectoListProyectoToAttach = em.getReference(proyectoListProyectoToAttach.getClass(), proyectoListProyectoToAttach.getIdproyecto());
                attachedProyectoList.add(proyectoListProyectoToAttach);
            }
            lineaaccion.setProyectoList(attachedProyectoList);
            em.persist(lineaaccion);
            for (Proyecto proyectoListProyecto : lineaaccion.getProyectoList()) {
                Lineaaccion oldLineaAccionOfProyectoListProyecto = proyectoListProyecto.getLineaAccion();
                proyectoListProyecto.setLineaAccion(lineaaccion);
                proyectoListProyecto = em.merge(proyectoListProyecto);
                if (oldLineaAccionOfProyectoListProyecto != null) {
                    oldLineaAccionOfProyectoListProyecto.getProyectoList().remove(proyectoListProyecto);
                    oldLineaAccionOfProyectoListProyecto = em.merge(oldLineaAccionOfProyectoListProyecto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Lineaaccion lineaaccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lineaaccion persistentLineaaccion = em.find(Lineaaccion.class, lineaaccion.getIdLa());
            List<Proyecto> proyectoListOld = persistentLineaaccion.getProyectoList();
            List<Proyecto> proyectoListNew = lineaaccion.getProyectoList();
            List<String> illegalOrphanMessages = null;
            for (Proyecto proyectoListOldProyecto : proyectoListOld) {
                if (!proyectoListNew.contains(proyectoListOldProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proyecto " + proyectoListOldProyecto + " since its lineaAccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Proyecto> attachedProyectoListNew = new ArrayList<Proyecto>();
            for (Proyecto proyectoListNewProyectoToAttach : proyectoListNew) {
                proyectoListNewProyectoToAttach = em.getReference(proyectoListNewProyectoToAttach.getClass(), proyectoListNewProyectoToAttach.getIdproyecto());
                attachedProyectoListNew.add(proyectoListNewProyectoToAttach);
            }
            proyectoListNew = attachedProyectoListNew;
            lineaaccion.setProyectoList(proyectoListNew);
            lineaaccion = em.merge(lineaaccion);
            for (Proyecto proyectoListNewProyecto : proyectoListNew) {
                if (!proyectoListOld.contains(proyectoListNewProyecto)) {
                    Lineaaccion oldLineaAccionOfProyectoListNewProyecto = proyectoListNewProyecto.getLineaAccion();
                    proyectoListNewProyecto.setLineaAccion(lineaaccion);
                    proyectoListNewProyecto = em.merge(proyectoListNewProyecto);
                    if (oldLineaAccionOfProyectoListNewProyecto != null && !oldLineaAccionOfProyectoListNewProyecto.equals(lineaaccion)) {
                        oldLineaAccionOfProyectoListNewProyecto.getProyectoList().remove(proyectoListNewProyecto);
                        oldLineaAccionOfProyectoListNewProyecto = em.merge(oldLineaAccionOfProyectoListNewProyecto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = lineaaccion.getIdLa();
                if (findLineaaccion(id) == null) {
                    throw new NonexistentEntityException("The lineaaccion with id " + id + " no longer exists.");
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
            Lineaaccion lineaaccion;
            try {
                lineaaccion = em.getReference(Lineaaccion.class, id);
                lineaaccion.getIdLa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lineaaccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Proyecto> proyectoListOrphanCheck = lineaaccion.getProyectoList();
            for (Proyecto proyectoListOrphanCheckProyecto : proyectoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Lineaaccion (" + lineaaccion + ") cannot be destroyed since the Proyecto " + proyectoListOrphanCheckProyecto + " in its proyectoList field has a non-nullable lineaAccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(lineaaccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Lineaaccion> findLineaaccionEntities() {
        return findLineaaccionEntities(true, -1, -1);
    }

    public List<Lineaaccion> findLineaaccionEntities(int maxResults, int firstResult) {
        return findLineaaccionEntities(false, maxResults, firstResult);
    }

    private List<Lineaaccion> findLineaaccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lineaaccion.class));
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

    public Lineaaccion findLineaaccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lineaaccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getLineaaccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lineaaccion> rt = cq.from(Lineaaccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
