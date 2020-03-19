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
import Entidades.Pcontratado;
import Entidades.Persona;
import Entidades.Socio;
import Entidades.Personal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Cristian
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pcontratado pcontratado = persona.getPcontratado();
            if (pcontratado != null) {
                pcontratado = em.getReference(pcontratado.getClass(), pcontratado.getIdPContratado());
                persona.setPcontratado(pcontratado);
            }
            Socio socio = persona.getSocio();
            if (socio != null) {
                socio = em.getReference(socio.getClass(), socio.getIdSocio());
                persona.setSocio(socio);
            }
            Personal personal = persona.getPersonal();
            if (personal != null) {
                personal = em.getReference(personal.getClass(), personal.getIdPersonal());
                persona.setPersonal(personal);
            }
            em.persist(persona);
            if (pcontratado != null) {
                Persona oldPersonaOfPcontratado = pcontratado.getPersona();
                if (oldPersonaOfPcontratado != null) {
                    oldPersonaOfPcontratado.setPcontratado(null);
                    oldPersonaOfPcontratado = em.merge(oldPersonaOfPcontratado);
                }
                pcontratado.setPersona(persona);
                pcontratado = em.merge(pcontratado);
            }
            if (socio != null) {
                Persona oldPersonaOfSocio = socio.getPersona();
                if (oldPersonaOfSocio != null) {
                    oldPersonaOfSocio.setSocio(null);
                    oldPersonaOfSocio = em.merge(oldPersonaOfSocio);
                }
                socio.setPersona(persona);
                socio = em.merge(socio);
            }
            if (personal != null) {
                Persona oldPersonaOfPersonal = personal.getPersona();
                if (oldPersonaOfPersonal != null) {
                    oldPersonaOfPersonal.setPersonal(null);
                    oldPersonaOfPersonal = em.merge(oldPersonaOfPersonal);
                }
                personal.setPersona(persona);
                personal = em.merge(personal);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getIdpersona());
            Pcontratado pcontratadoOld = persistentPersona.getPcontratado();
            Pcontratado pcontratadoNew = persona.getPcontratado();
            Socio socioOld = persistentPersona.getSocio();
            Socio socioNew = persona.getSocio();
            Personal personalOld = persistentPersona.getPersonal();
            Personal personalNew = persona.getPersonal();
            List<String> illegalOrphanMessages = null;
            if (pcontratadoOld != null && !pcontratadoOld.equals(pcontratadoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Pcontratado " + pcontratadoOld + " since its persona field is not nullable.");
            }
            if (socioOld != null && !socioOld.equals(socioNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Socio " + socioOld + " since its persona field is not nullable.");
            }
            if (personalOld != null && !personalOld.equals(personalNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Personal " + personalOld + " since its persona field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pcontratadoNew != null) {
                pcontratadoNew = em.getReference(pcontratadoNew.getClass(), pcontratadoNew.getIdPContratado());
                persona.setPcontratado(pcontratadoNew);
            }
            if (socioNew != null) {
                socioNew = em.getReference(socioNew.getClass(), socioNew.getIdSocio());
                persona.setSocio(socioNew);
            }
            if (personalNew != null) {
                personalNew = em.getReference(personalNew.getClass(), personalNew.getIdPersonal());
                persona.setPersonal(personalNew);
            }
            persona = em.merge(persona);
            if (pcontratadoNew != null && !pcontratadoNew.equals(pcontratadoOld)) {
                Persona oldPersonaOfPcontratado = pcontratadoNew.getPersona();
                if (oldPersonaOfPcontratado != null) {
                    oldPersonaOfPcontratado.setPcontratado(null);
                    oldPersonaOfPcontratado = em.merge(oldPersonaOfPcontratado);
                }
                pcontratadoNew.setPersona(persona);
                pcontratadoNew = em.merge(pcontratadoNew);
            }
            if (socioNew != null && !socioNew.equals(socioOld)) {
                Persona oldPersonaOfSocio = socioNew.getPersona();
                if (oldPersonaOfSocio != null) {
                    oldPersonaOfSocio.setSocio(null);
                    oldPersonaOfSocio = em.merge(oldPersonaOfSocio);
                }
                socioNew.setPersona(persona);
                socioNew = em.merge(socioNew);
            }
            if (personalNew != null && !personalNew.equals(personalOld)) {
                Persona oldPersonaOfPersonal = personalNew.getPersona();
                if (oldPersonaOfPersonal != null) {
                    oldPersonaOfPersonal.setPersonal(null);
                    oldPersonaOfPersonal = em.merge(oldPersonaOfPersonal);
                }
                personalNew.setPersona(persona);
                personalNew = em.merge(personalNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getIdpersona();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getIdpersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Pcontratado pcontratadoOrphanCheck = persona.getPcontratado();
            if (pcontratadoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Pcontratado " + pcontratadoOrphanCheck + " in its pcontratado field has a non-nullable persona field.");
            }
            Socio socioOrphanCheck = persona.getSocio();
            if (socioOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Socio " + socioOrphanCheck + " in its socio field has a non-nullable persona field.");
            }
            Personal personalOrphanCheck = persona.getPersonal();
            if (personalOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Personal " + personalOrphanCheck + " in its personal field has a non-nullable persona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
