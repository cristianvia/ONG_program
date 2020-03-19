/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Ingresos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Ipublicos;
import java.util.ArrayList;
import java.util.List;
import Entidades.Iprivados;
import Entidades.Ong;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Cristian
 */
public class IngresosJpaController implements Serializable {

    public IngresosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingresos ingresos) {
        if (ingresos.getIpublicosList() == null) {
            ingresos.setIpublicosList(new ArrayList<Ipublicos>());
        }
        if (ingresos.getIprivadosList() == null) {
            ingresos.setIprivadosList(new ArrayList<Iprivados>());
        }
        if (ingresos.getOngList() == null) {
            ingresos.setOngList(new ArrayList<Ong>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ipublicos> attachedIpublicosList = new ArrayList<Ipublicos>();
            for (Ipublicos ipublicosListIpublicosToAttach : ingresos.getIpublicosList()) {
                ipublicosListIpublicosToAttach = em.getReference(ipublicosListIpublicosToAttach.getClass(), ipublicosListIpublicosToAttach.getIdIPublicos());
                attachedIpublicosList.add(ipublicosListIpublicosToAttach);
            }
            ingresos.setIpublicosList(attachedIpublicosList);
            List<Iprivados> attachedIprivadosList = new ArrayList<Iprivados>();
            for (Iprivados iprivadosListIprivadosToAttach : ingresos.getIprivadosList()) {
                iprivadosListIprivadosToAttach = em.getReference(iprivadosListIprivadosToAttach.getClass(), iprivadosListIprivadosToAttach.getIdiPrivados());
                attachedIprivadosList.add(iprivadosListIprivadosToAttach);
            }
            ingresos.setIprivadosList(attachedIprivadosList);
            List<Ong> attachedOngList = new ArrayList<Ong>();
            for (Ong ongListOngToAttach : ingresos.getOngList()) {
                ongListOngToAttach = em.getReference(ongListOngToAttach.getClass(), ongListOngToAttach.getOngPK());
                attachedOngList.add(ongListOngToAttach);
            }
            ingresos.setOngList(attachedOngList);
            em.persist(ingresos);
            for (Ipublicos ipublicosListIpublicos : ingresos.getIpublicosList()) {
                ipublicosListIpublicos.getIngresosList().add(ingresos);
                ipublicosListIpublicos = em.merge(ipublicosListIpublicos);
            }
            for (Iprivados iprivadosListIprivados : ingresos.getIprivadosList()) {
                iprivadosListIprivados.getIngresosList().add(ingresos);
                iprivadosListIprivados = em.merge(iprivadosListIprivados);
            }
            for (Ong ongListOng : ingresos.getOngList()) {
                Ingresos oldIngresosidIngresosOfOngListOng = ongListOng.getIngresosidIngresos();
                ongListOng.setIngresosidIngresos(ingresos);
                ongListOng = em.merge(ongListOng);
                if (oldIngresosidIngresosOfOngListOng != null) {
                    oldIngresosidIngresosOfOngListOng.getOngList().remove(ongListOng);
                    oldIngresosidIngresosOfOngListOng = em.merge(oldIngresosidIngresosOfOngListOng);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingresos ingresos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingresos persistentIngresos = em.find(Ingresos.class, ingresos.getIdIngresos());
            List<Ipublicos> ipublicosListOld = persistentIngresos.getIpublicosList();
            List<Ipublicos> ipublicosListNew = ingresos.getIpublicosList();
            List<Iprivados> iprivadosListOld = persistentIngresos.getIprivadosList();
            List<Iprivados> iprivadosListNew = ingresos.getIprivadosList();
            List<Ong> ongListOld = persistentIngresos.getOngList();
            List<Ong> ongListNew = ingresos.getOngList();
            List<String> illegalOrphanMessages = null;
            for (Ong ongListOldOng : ongListOld) {
                if (!ongListNew.contains(ongListOldOng)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ong " + ongListOldOng + " since its ingresosidIngresos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Ipublicos> attachedIpublicosListNew = new ArrayList<Ipublicos>();
            for (Ipublicos ipublicosListNewIpublicosToAttach : ipublicosListNew) {
                ipublicosListNewIpublicosToAttach = em.getReference(ipublicosListNewIpublicosToAttach.getClass(), ipublicosListNewIpublicosToAttach.getIdIPublicos());
                attachedIpublicosListNew.add(ipublicosListNewIpublicosToAttach);
            }
            ipublicosListNew = attachedIpublicosListNew;
            ingresos.setIpublicosList(ipublicosListNew);
            List<Iprivados> attachedIprivadosListNew = new ArrayList<Iprivados>();
            for (Iprivados iprivadosListNewIprivadosToAttach : iprivadosListNew) {
                iprivadosListNewIprivadosToAttach = em.getReference(iprivadosListNewIprivadosToAttach.getClass(), iprivadosListNewIprivadosToAttach.getIdiPrivados());
                attachedIprivadosListNew.add(iprivadosListNewIprivadosToAttach);
            }
            iprivadosListNew = attachedIprivadosListNew;
            ingresos.setIprivadosList(iprivadosListNew);
            List<Ong> attachedOngListNew = new ArrayList<Ong>();
            for (Ong ongListNewOngToAttach : ongListNew) {
                ongListNewOngToAttach = em.getReference(ongListNewOngToAttach.getClass(), ongListNewOngToAttach.getOngPK());
                attachedOngListNew.add(ongListNewOngToAttach);
            }
            ongListNew = attachedOngListNew;
            ingresos.setOngList(ongListNew);
            ingresos = em.merge(ingresos);
            for (Ipublicos ipublicosListOldIpublicos : ipublicosListOld) {
                if (!ipublicosListNew.contains(ipublicosListOldIpublicos)) {
                    ipublicosListOldIpublicos.getIngresosList().remove(ingresos);
                    ipublicosListOldIpublicos = em.merge(ipublicosListOldIpublicos);
                }
            }
            for (Ipublicos ipublicosListNewIpublicos : ipublicosListNew) {
                if (!ipublicosListOld.contains(ipublicosListNewIpublicos)) {
                    ipublicosListNewIpublicos.getIngresosList().add(ingresos);
                    ipublicosListNewIpublicos = em.merge(ipublicosListNewIpublicos);
                }
            }
            for (Iprivados iprivadosListOldIprivados : iprivadosListOld) {
                if (!iprivadosListNew.contains(iprivadosListOldIprivados)) {
                    iprivadosListOldIprivados.getIngresosList().remove(ingresos);
                    iprivadosListOldIprivados = em.merge(iprivadosListOldIprivados);
                }
            }
            for (Iprivados iprivadosListNewIprivados : iprivadosListNew) {
                if (!iprivadosListOld.contains(iprivadosListNewIprivados)) {
                    iprivadosListNewIprivados.getIngresosList().add(ingresos);
                    iprivadosListNewIprivados = em.merge(iprivadosListNewIprivados);
                }
            }
            for (Ong ongListNewOng : ongListNew) {
                if (!ongListOld.contains(ongListNewOng)) {
                    Ingresos oldIngresosidIngresosOfOngListNewOng = ongListNewOng.getIngresosidIngresos();
                    ongListNewOng.setIngresosidIngresos(ingresos);
                    ongListNewOng = em.merge(ongListNewOng);
                    if (oldIngresosidIngresosOfOngListNewOng != null && !oldIngresosidIngresosOfOngListNewOng.equals(ingresos)) {
                        oldIngresosidIngresosOfOngListNewOng.getOngList().remove(ongListNewOng);
                        oldIngresosidIngresosOfOngListNewOng = em.merge(oldIngresosidIngresosOfOngListNewOng);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ingresos.getIdIngresos();
                if (findIngresos(id) == null) {
                    throw new NonexistentEntityException("The ingresos with id " + id + " no longer exists.");
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
            Ingresos ingresos;
            try {
                ingresos = em.getReference(Ingresos.class, id);
                ingresos.getIdIngresos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingresos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ong> ongListOrphanCheck = ingresos.getOngList();
            for (Ong ongListOrphanCheckOng : ongListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ingresos (" + ingresos + ") cannot be destroyed since the Ong " + ongListOrphanCheckOng + " in its ongList field has a non-nullable ingresosidIngresos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Ipublicos> ipublicosList = ingresos.getIpublicosList();
            for (Ipublicos ipublicosListIpublicos : ipublicosList) {
                ipublicosListIpublicos.getIngresosList().remove(ingresos);
                ipublicosListIpublicos = em.merge(ipublicosListIpublicos);
            }
            List<Iprivados> iprivadosList = ingresos.getIprivadosList();
            for (Iprivados iprivadosListIprivados : iprivadosList) {
                iprivadosListIprivados.getIngresosList().remove(ingresos);
                iprivadosListIprivados = em.merge(iprivadosListIprivados);
            }
            em.remove(ingresos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingresos> findIngresosEntities() {
        return findIngresosEntities(true, -1, -1);
    }

    public List<Ingresos> findIngresosEntities(int maxResults, int firstResult) {
        return findIngresosEntities(false, maxResults, firstResult);
    }

    private List<Ingresos> findIngresosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingresos.class));
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

    public Ingresos findIngresos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingresos.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngresosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingresos> rt = cq.from(Ingresos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
