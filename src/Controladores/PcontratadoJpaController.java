/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Pcontratado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Cristian
 */
public class PcontratadoJpaController implements Serializable {

    public PcontratadoJpaController() {
        //Indicamos cual es nuestra unidad de persistencia
        this.emf = Persistence.createEntityManagerFactory("ONGPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pcontratado pcontratado) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Persona personaOrphanCheck = pcontratado.getPersona();
        if (personaOrphanCheck != null) {
            Pcontratado oldPcontratadoOfPersona = personaOrphanCheck.getPcontratado();
            if (oldPcontratadoOfPersona != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Persona " + personaOrphanCheck + " already has an item of type Pcontratado whose persona column cannot be null. Please make another selection for the persona field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona = pcontratado.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getIdpersona());
                pcontratado.setPersona(persona);
            }
            em.persist(pcontratado);
            if (persona != null) {
                persona.setPcontratado(pcontratado);
                persona = em.merge(persona);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPcontratado(pcontratado.getIdPContratado()) != null) {
                throw new PreexistingEntityException("Pcontratado " + pcontratado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pcontratado pcontratado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pcontratado persistentPcontratado = em.find(Pcontratado.class, pcontratado.getIdPContratado());
            Persona personaOld = persistentPcontratado.getPersona();
            Persona personaNew = pcontratado.getPersona();
            List<String> illegalOrphanMessages = null;
            if (personaNew != null && !personaNew.equals(personaOld)) {
                Pcontratado oldPcontratadoOfPersona = personaNew.getPcontratado();
                if (oldPcontratadoOfPersona != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Persona " + personaNew + " already has an item of type Pcontratado whose persona column cannot be null. Please make another selection for the persona field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getIdpersona());
                pcontratado.setPersona(personaNew);
            }
            pcontratado = em.merge(pcontratado);
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.setPcontratado(null);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.setPcontratado(pcontratado);
                personaNew = em.merge(personaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pcontratado.getIdPContratado();
                if (findPcontratado(id) == null) {
                    throw new NonexistentEntityException("The pcontratado with id " + id + " no longer exists.");
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
            Pcontratado pcontratado;
            try {
                pcontratado = em.getReference(Pcontratado.class, id);
                pcontratado.getIdPContratado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pcontratado with id " + id + " no longer exists.", enfe);
            }
            Persona persona = pcontratado.getPersona();
            if (persona != null) {
                persona.setPcontratado(null);
                persona = em.merge(persona);
            }
            em.remove(pcontratado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pcontratado> findPcontratadoEntities() {
        return findPcontratadoEntities(true, -1, -1);
    }

    public List<Pcontratado> findPcontratadoEntities(int maxResults, int firstResult) {
        return findPcontratadoEntities(false, maxResults, firstResult);
    }

    private List<Pcontratado> findPcontratadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pcontratado.class));
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

    public Pcontratado findPcontratado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pcontratado.class, id);
        } finally {
            em.close();
        }
    }

    public int getPcontratadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pcontratado> rt = cq.from(Pcontratado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
