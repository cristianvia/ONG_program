/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Delegacion;
import Entidades.Ingresos;
import Entidades.Ong;
import Entidades.OngPK;
import Entidades.Sedecentral;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Cristian
 */
public class OngJpaController implements Serializable {

    public OngJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ong ong) throws PreexistingEntityException, Exception {
        if (ong.getOngPK() == null) {
            ong.setOngPK(new OngPK());
        }
        ong.getOngPK().setDelegaciones(ong.getDelegacion().getIddelegacion());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Delegacion delegacion = ong.getDelegacion();
            if (delegacion != null) {
                delegacion = em.getReference(delegacion.getClass(), delegacion.getIddelegacion());
                ong.setDelegacion(delegacion);
            }
            Ingresos ingresosidIngresos = ong.getIngresosidIngresos();
            if (ingresosidIngresos != null) {
                ingresosidIngresos = em.getReference(ingresosidIngresos.getClass(), ingresosidIngresos.getIdIngresos());
                ong.setIngresosidIngresos(ingresosidIngresos);
            }
            Sedecentral sedeCentralidSedeCentral = ong.getSedeCentralidSedeCentral();
            if (sedeCentralidSedeCentral != null) {
                sedeCentralidSedeCentral = em.getReference(sedeCentralidSedeCentral.getClass(), sedeCentralidSedeCentral.getIdSedeCentral());
                ong.setSedeCentralidSedeCentral(sedeCentralidSedeCentral);
            }
            em.persist(ong);
            if (delegacion != null) {
                delegacion.getOngList().add(ong);
                delegacion = em.merge(delegacion);
            }
            if (ingresosidIngresos != null) {
                ingresosidIngresos.getOngList().add(ong);
                ingresosidIngresos = em.merge(ingresosidIngresos);
            }
            if (sedeCentralidSedeCentral != null) {
                sedeCentralidSedeCentral.getOngList().add(ong);
                sedeCentralidSedeCentral = em.merge(sedeCentralidSedeCentral);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOng(ong.getOngPK()) != null) {
                throw new PreexistingEntityException("Ong " + ong + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ong ong) throws NonexistentEntityException, Exception {
        ong.getOngPK().setDelegaciones(ong.getDelegacion().getIddelegacion());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ong persistentOng = em.find(Ong.class, ong.getOngPK());
            Delegacion delegacionOld = persistentOng.getDelegacion();
            Delegacion delegacionNew = ong.getDelegacion();
            Ingresos ingresosidIngresosOld = persistentOng.getIngresosidIngresos();
            Ingresos ingresosidIngresosNew = ong.getIngresosidIngresos();
            Sedecentral sedeCentralidSedeCentralOld = persistentOng.getSedeCentralidSedeCentral();
            Sedecentral sedeCentralidSedeCentralNew = ong.getSedeCentralidSedeCentral();
            if (delegacionNew != null) {
                delegacionNew = em.getReference(delegacionNew.getClass(), delegacionNew.getIddelegacion());
                ong.setDelegacion(delegacionNew);
            }
            if (ingresosidIngresosNew != null) {
                ingresosidIngresosNew = em.getReference(ingresosidIngresosNew.getClass(), ingresosidIngresosNew.getIdIngresos());
                ong.setIngresosidIngresos(ingresosidIngresosNew);
            }
            if (sedeCentralidSedeCentralNew != null) {
                sedeCentralidSedeCentralNew = em.getReference(sedeCentralidSedeCentralNew.getClass(), sedeCentralidSedeCentralNew.getIdSedeCentral());
                ong.setSedeCentralidSedeCentral(sedeCentralidSedeCentralNew);
            }
            ong = em.merge(ong);
            if (delegacionOld != null && !delegacionOld.equals(delegacionNew)) {
                delegacionOld.getOngList().remove(ong);
                delegacionOld = em.merge(delegacionOld);
            }
            if (delegacionNew != null && !delegacionNew.equals(delegacionOld)) {
                delegacionNew.getOngList().add(ong);
                delegacionNew = em.merge(delegacionNew);
            }
            if (ingresosidIngresosOld != null && !ingresosidIngresosOld.equals(ingresosidIngresosNew)) {
                ingresosidIngresosOld.getOngList().remove(ong);
                ingresosidIngresosOld = em.merge(ingresosidIngresosOld);
            }
            if (ingresosidIngresosNew != null && !ingresosidIngresosNew.equals(ingresosidIngresosOld)) {
                ingresosidIngresosNew.getOngList().add(ong);
                ingresosidIngresosNew = em.merge(ingresosidIngresosNew);
            }
            if (sedeCentralidSedeCentralOld != null && !sedeCentralidSedeCentralOld.equals(sedeCentralidSedeCentralNew)) {
                sedeCentralidSedeCentralOld.getOngList().remove(ong);
                sedeCentralidSedeCentralOld = em.merge(sedeCentralidSedeCentralOld);
            }
            if (sedeCentralidSedeCentralNew != null && !sedeCentralidSedeCentralNew.equals(sedeCentralidSedeCentralOld)) {
                sedeCentralidSedeCentralNew.getOngList().add(ong);
                sedeCentralidSedeCentralNew = em.merge(sedeCentralidSedeCentralNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                OngPK id = ong.getOngPK();
                if (findOng(id) == null) {
                    throw new NonexistentEntityException("The ong with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(OngPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ong ong;
            try {
                ong = em.getReference(Ong.class, id);
                ong.getOngPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ong with id " + id + " no longer exists.", enfe);
            }
            Delegacion delegacion = ong.getDelegacion();
            if (delegacion != null) {
                delegacion.getOngList().remove(ong);
                delegacion = em.merge(delegacion);
            }
            Ingresos ingresosidIngresos = ong.getIngresosidIngresos();
            if (ingresosidIngresos != null) {
                ingresosidIngresos.getOngList().remove(ong);
                ingresosidIngresos = em.merge(ingresosidIngresos);
            }
            Sedecentral sedeCentralidSedeCentral = ong.getSedeCentralidSedeCentral();
            if (sedeCentralidSedeCentral != null) {
                sedeCentralidSedeCentral.getOngList().remove(ong);
                sedeCentralidSedeCentral = em.merge(sedeCentralidSedeCentral);
            }
            em.remove(ong);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ong> findOngEntities() {
        return findOngEntities(true, -1, -1);
    }

    public List<Ong> findOngEntities(int maxResults, int firstResult) {
        return findOngEntities(false, maxResults, firstResult);
    }

    private List<Ong> findOngEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ong.class));
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

    public Ong findOng(OngPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ong.class, id);
        } finally {
            em.close();
        }
    }

    public int getOngCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ong> rt = cq.from(Ong.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
