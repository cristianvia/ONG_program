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
import Entidades.Proyecto;
import java.util.ArrayList;
import java.util.List;
import Entidades.Iprivados;
import Entidades.Socio;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Cristian
 */
public class SocioJpaController implements Serializable {

    public SocioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Socio socio) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (socio.getProyectoList() == null) {
            socio.setProyectoList(new ArrayList<Proyecto>());
        }
        if (socio.getIprivadosList() == null) {
            socio.setIprivadosList(new ArrayList<Iprivados>());
        }
        List<String> illegalOrphanMessages = null;
        Persona personaOrphanCheck = socio.getPersona();
        if (personaOrphanCheck != null) {
            Socio oldSocioOfPersona = personaOrphanCheck.getSocio();
            if (oldSocioOfPersona != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Persona " + personaOrphanCheck + " already has an item of type Socio whose persona column cannot be null. Please make another selection for the persona field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona = socio.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getIdpersona());
                socio.setPersona(persona);
            }
            List<Proyecto> attachedProyectoList = new ArrayList<Proyecto>();
            for (Proyecto proyectoListProyectoToAttach : socio.getProyectoList()) {
                proyectoListProyectoToAttach = em.getReference(proyectoListProyectoToAttach.getClass(), proyectoListProyectoToAttach.getIdproyecto());
                attachedProyectoList.add(proyectoListProyectoToAttach);
            }
            socio.setProyectoList(attachedProyectoList);
            List<Iprivados> attachedIprivadosList = new ArrayList<Iprivados>();
            for (Iprivados iprivadosListIprivadosToAttach : socio.getIprivadosList()) {
                iprivadosListIprivadosToAttach = em.getReference(iprivadosListIprivadosToAttach.getClass(), iprivadosListIprivadosToAttach.getIdiPrivados());
                attachedIprivadosList.add(iprivadosListIprivadosToAttach);
            }
            socio.setIprivadosList(attachedIprivadosList);
            em.persist(socio);
            if (persona != null) {
                persona.setSocio(socio);
                persona = em.merge(persona);
            }
            for (Proyecto proyectoListProyecto : socio.getProyectoList()) {
                Socio oldSocioLocalOfProyectoListProyecto = proyectoListProyecto.getSocioLocal();
                proyectoListProyecto.setSocioLocal(socio);
                proyectoListProyecto = em.merge(proyectoListProyecto);
                if (oldSocioLocalOfProyectoListProyecto != null) {
                    oldSocioLocalOfProyectoListProyecto.getProyectoList().remove(proyectoListProyecto);
                    oldSocioLocalOfProyectoListProyecto = em.merge(oldSocioLocalOfProyectoListProyecto);
                }
            }
            for (Iprivados iprivadosListIprivados : socio.getIprivadosList()) {
                Socio oldIdSocioOfIprivadosListIprivados = iprivadosListIprivados.getIdSocio();
                iprivadosListIprivados.setIdSocio(socio);
                iprivadosListIprivados = em.merge(iprivadosListIprivados);
                if (oldIdSocioOfIprivadosListIprivados != null) {
                    oldIdSocioOfIprivadosListIprivados.getIprivadosList().remove(iprivadosListIprivados);
                    oldIdSocioOfIprivadosListIprivados = em.merge(oldIdSocioOfIprivadosListIprivados);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSocio(socio.getIdSocio()) != null) {
                throw new PreexistingEntityException("Socio " + socio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Socio socio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Socio persistentSocio = em.find(Socio.class, socio.getIdSocio());
            Persona personaOld = persistentSocio.getPersona();
            Persona personaNew = socio.getPersona();
            List<Proyecto> proyectoListOld = persistentSocio.getProyectoList();
            List<Proyecto> proyectoListNew = socio.getProyectoList();
            List<Iprivados> iprivadosListOld = persistentSocio.getIprivadosList();
            List<Iprivados> iprivadosListNew = socio.getIprivadosList();
            List<String> illegalOrphanMessages = null;
            if (personaNew != null && !personaNew.equals(personaOld)) {
                Socio oldSocioOfPersona = personaNew.getSocio();
                if (oldSocioOfPersona != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Persona " + personaNew + " already has an item of type Socio whose persona column cannot be null. Please make another selection for the persona field.");
                }
            }
            for (Proyecto proyectoListOldProyecto : proyectoListOld) {
                if (!proyectoListNew.contains(proyectoListOldProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proyecto " + proyectoListOldProyecto + " since its socioLocal field is not nullable.");
                }
            }
            for (Iprivados iprivadosListOldIprivados : iprivadosListOld) {
                if (!iprivadosListNew.contains(iprivadosListOldIprivados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Iprivados " + iprivadosListOldIprivados + " since its idSocio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getIdpersona());
                socio.setPersona(personaNew);
            }
            List<Proyecto> attachedProyectoListNew = new ArrayList<Proyecto>();
            for (Proyecto proyectoListNewProyectoToAttach : proyectoListNew) {
                proyectoListNewProyectoToAttach = em.getReference(proyectoListNewProyectoToAttach.getClass(), proyectoListNewProyectoToAttach.getIdproyecto());
                attachedProyectoListNew.add(proyectoListNewProyectoToAttach);
            }
            proyectoListNew = attachedProyectoListNew;
            socio.setProyectoList(proyectoListNew);
            List<Iprivados> attachedIprivadosListNew = new ArrayList<Iprivados>();
            for (Iprivados iprivadosListNewIprivadosToAttach : iprivadosListNew) {
                iprivadosListNewIprivadosToAttach = em.getReference(iprivadosListNewIprivadosToAttach.getClass(), iprivadosListNewIprivadosToAttach.getIdiPrivados());
                attachedIprivadosListNew.add(iprivadosListNewIprivadosToAttach);
            }
            iprivadosListNew = attachedIprivadosListNew;
            socio.setIprivadosList(iprivadosListNew);
            socio = em.merge(socio);
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.setSocio(null);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.setSocio(socio);
                personaNew = em.merge(personaNew);
            }
            for (Proyecto proyectoListNewProyecto : proyectoListNew) {
                if (!proyectoListOld.contains(proyectoListNewProyecto)) {
                    Socio oldSocioLocalOfProyectoListNewProyecto = proyectoListNewProyecto.getSocioLocal();
                    proyectoListNewProyecto.setSocioLocal(socio);
                    proyectoListNewProyecto = em.merge(proyectoListNewProyecto);
                    if (oldSocioLocalOfProyectoListNewProyecto != null && !oldSocioLocalOfProyectoListNewProyecto.equals(socio)) {
                        oldSocioLocalOfProyectoListNewProyecto.getProyectoList().remove(proyectoListNewProyecto);
                        oldSocioLocalOfProyectoListNewProyecto = em.merge(oldSocioLocalOfProyectoListNewProyecto);
                    }
                }
            }
            for (Iprivados iprivadosListNewIprivados : iprivadosListNew) {
                if (!iprivadosListOld.contains(iprivadosListNewIprivados)) {
                    Socio oldIdSocioOfIprivadosListNewIprivados = iprivadosListNewIprivados.getIdSocio();
                    iprivadosListNewIprivados.setIdSocio(socio);
                    iprivadosListNewIprivados = em.merge(iprivadosListNewIprivados);
                    if (oldIdSocioOfIprivadosListNewIprivados != null && !oldIdSocioOfIprivadosListNewIprivados.equals(socio)) {
                        oldIdSocioOfIprivadosListNewIprivados.getIprivadosList().remove(iprivadosListNewIprivados);
                        oldIdSocioOfIprivadosListNewIprivados = em.merge(oldIdSocioOfIprivadosListNewIprivados);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = socio.getIdSocio();
                if (findSocio(id) == null) {
                    throw new NonexistentEntityException("The socio with id " + id + " no longer exists.");
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
            Socio socio;
            try {
                socio = em.getReference(Socio.class, id);
                socio.getIdSocio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The socio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Proyecto> proyectoListOrphanCheck = socio.getProyectoList();
            for (Proyecto proyectoListOrphanCheckProyecto : proyectoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Socio (" + socio + ") cannot be destroyed since the Proyecto " + proyectoListOrphanCheckProyecto + " in its proyectoList field has a non-nullable socioLocal field.");
            }
            List<Iprivados> iprivadosListOrphanCheck = socio.getIprivadosList();
            for (Iprivados iprivadosListOrphanCheckIprivados : iprivadosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Socio (" + socio + ") cannot be destroyed since the Iprivados " + iprivadosListOrphanCheckIprivados + " in its iprivadosList field has a non-nullable idSocio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Persona persona = socio.getPersona();
            if (persona != null) {
                persona.setSocio(null);
                persona = em.merge(persona);
            }
            em.remove(socio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Socio> findSocioEntities() {
        return findSocioEntities(true, -1, -1);
    }

    public List<Socio> findSocioEntities(int maxResults, int firstResult) {
        return findSocioEntities(false, maxResults, firstResult);
    }

    private List<Socio> findSocioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Socio.class));
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

    public Socio findSocio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Socio.class, id);
        } finally {
            em.close();
        }
    }

    public int getSocioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Socio> rt = cq.from(Socio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
