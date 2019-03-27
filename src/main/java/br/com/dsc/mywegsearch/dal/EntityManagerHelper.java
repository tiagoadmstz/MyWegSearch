/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dsc.mywegsearch.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;

/**
 *
 * @author Tiago D. Teixeira
 * @param <T>
 */
public class EntityManagerHelper<T> {

    private final Map<String, ThreadLocal<EntityManager>> sessions = new HashMap<>();
    private static final Logger LOG = Logger.getLogger(EntityManagerHelper.class.getName());
    private static final Map<String, String> propMap = new HashMap();
    public final static int SALVAR = 0, ATUALIZAR = 1, DELETAR = 2;
    public final static String DERBY_PU = "DERBY_PU";

    public EntityManagerHelper() {
    }

    public synchronized boolean getOperation(int operation_type, Object object, String persistence_unit) {
        EntityManager session = getSession(persistence_unit);
        EntityTransaction transaction = session.getTransaction();
        try {
            transaction.begin();
            switch (operation_type) {
                case SALVAR:
                    LOG.info("Salvando registro no banco de dados");
                    session.persist(object);
                    session.getTransaction().commit();
                    break;
                case ATUALIZAR:
                    LOG.info("Atualizando registro no banco de dados");
                    session.merge(object);
                    session.getTransaction().commit();
                    break;
                case DELETAR:
                    LOG.info("Deletando registro no banco de dados");
                    session.remove(session.merge(object));
                    session.getTransaction().commit();
                    break;
            }
            this.closeSession(persistence_unit);
            return true;
        } catch (Exception e) {
            //transaction.rollback();
            this.closeSession(persistence_unit);
            e.printStackTrace();
            return false;
        }
    }

    public boolean save(Object object, String persistence_unit) {
        EntityManager session = getSession(persistence_unit);
        EntityTransaction transaction = session.getTransaction();
        try {
            transaction.begin();
            LOG.info("Salvando registro no banco de dados");
            session.persist(object);
            session.getTransaction().commit();
            this.closeSession(persistence_unit);
            return true;
        } catch (Exception e) {
            //transaction.rollback();
            this.closeSession(persistence_unit);
            e.printStackTrace();
            return false;
        }
    }

    private synchronized EntityManager getSession(String persistence_unit) {
        EntityManager session = null;
        if (sessions.isEmpty()) {
            sessions.put(persistence_unit, new ThreadLocal());
            session = sessions.get(persistence_unit).get();
            session = session == null ? EntityManagerFactoryService.getEntityManagerFactory(persistence_unit, propMap).createEntityManager() : session;
        } else {
            session = sessions.get(persistence_unit).get();
            session = session == null ? EntityManagerFactoryService.getEntityManagerFactory(persistence_unit, propMap).createEntityManager() : session;
        }
        return session;
    }

    private synchronized void closeSession(String persistence_unit) {
        EntityManager session = null;
        if (!sessions.isEmpty()) {
            session = sessions.get(persistence_unit).get();
            LOG.info("Encerrando sessão do banco de dados");
            if (session != null) {
                if (session.isOpen()) {
                    session.close();
                }
            }
        }
        LOG.info("Removendo Entity Manager desta sessão");
        sessions.remove(persistence_unit);
    }

    public void closeAll() {
        LOG.info("Encerrando todas as sessões");
        sessions.clear();
    }

    public synchronized Connection getConnection(String persistence_unit) {
        try {
            EntityManager entityManager = getSession(persistence_unit);
            Connection conn = ((EntityManagerImpl) (entityManager.getDelegate())).getServerSession().getAccessor().getConnection();
            return conn;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean enableTrigger(String triggerName, String persistence_unit, boolean enable) {
        try {
            String sql = "ALTER TRIGGER \"PLANO\".\"" + triggerName + "\" ";
            if (enable) {
                sql = sql.concat("ENABLE");
            } else {
                sql = sql.concat("DISABLE");
            }
            Connection conn = this.getConnection(persistence_unit);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            conn.close();
            return true;
        } catch (Exception e) {
            this.closeSession(persistence_unit);
            e.printStackTrace();
            return false;
        }
    }

    public List<?> getObjectList(String strHQL, String persistence_unit) {
        try {
            EntityManager session = this.getSession(persistence_unit);
            session.getTransaction().begin();
            Query query = session.createQuery(strHQL);
            List<?> objects = query.getResultList();
            this.closeSession(persistence_unit);
            return objects;
        } catch (Exception e) {
            this.closeSession(persistence_unit);
            e.printStackTrace();
            return null;
        }
    }

    public List<?> getObjectList(String strHQL, int firstResult, int maxResults, String persistence_unit) {
        try {
            EntityManager session = this.getSession(persistence_unit);
            session.getTransaction().begin();
            Query query = session.createQuery(strHQL);
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
            List<?> objects = query.getResultList();
            this.closeSession(persistence_unit);
            return objects;
        } catch (Exception e) {
            this.closeSession(persistence_unit);
            e.printStackTrace();
            return null;
        }
    }

    public Optional<List<?>> getObjectList(Class entity, String strHQL, Map<String, Object> parameters, String persistence_unit) {
        try {
            EntityManager session = this.getSession(persistence_unit);
            Query q = session.createQuery(strHQL, entity);
            session.getEntityManagerFactory().addNamedQuery("dynamicquery", q);
            Query query = session.createNamedQuery("dynamicquery", entity);
            parameters.forEach((k, v) -> query.setParameter(k, v));
            session.getTransaction().begin();
            List<?> objects = query.getResultList();
            this.closeSession(persistence_unit);
            return Optional.ofNullable(objects);
        } catch (Exception e) {
            e.printStackTrace();
            this.closeSession(persistence_unit);
            return Optional.empty();
        }
    }

    public List<?> getObjectList(String strHQL, String strParam, Object valor, String persistence_unit) {
        try {
            EntityManager session = this.getSession(persistence_unit);
            session.getTransaction().begin();
            Query query = session.createQuery(strHQL);
            query.setParameter(strParam, valor);
            List<?> objects = query.getResultList();
            this.closeSession(persistence_unit);
            return objects;
        } catch (Exception e) {
            this.closeSession(persistence_unit);
            return null;
        }
    }

    public Optional<List<?>> getObjectListNamedQuery(Class classType, String namedQuery, String[] strParam, Object[] valor, String persistence_unit) {
        try {
            Query query = getNamedQuery(classType, namedQuery, persistence_unit);
            setParameters(query, strParam, valor);
            Optional<List<?>> objects = Optional.ofNullable(query.getResultList());
            this.closeSession(persistence_unit);
            return objects;
        } catch (Exception e) {
            e.printStackTrace();
            this.closeSession(persistence_unit);
            return Optional.empty();
        }
    }

    public Optional<List<?>> getObjectListNamedQuery(Class classType, int maxResults, String namedQuery, String[] strParam, Object[] valor, String persistence_unit) {
        try {
            Query query = getNamedQuery(classType, namedQuery, persistence_unit);
            setParameters(query, strParam, valor);
            query.setMaxResults(maxResults);
            Optional<List<?>> objects = Optional.ofNullable(query.getResultList());
            this.closeSession(persistence_unit);
            return objects;
        } catch (Exception e) {
            e.printStackTrace();
            this.closeSession(persistence_unit);
            return Optional.empty();
        }
    }

    public Optional<?> getObjectNamedQuery(Class classType, String namedQuery, String strParam, Object valor, String persistence_unit) {
        try {
            Query query = getNamedQuery(classType, namedQuery, persistence_unit);
            if (strParam != null) {
                query.setParameter(strParam, valor);
            }
            Object object = query.getSingleResult();
            this.closeSession(persistence_unit);
            return Optional.ofNullable(object);
        } catch (Exception e) {
            e.printStackTrace();
            this.closeSession(persistence_unit);
            return Optional.empty();
        }
    }

    public List<?> getObjectList(String strHQL, String strParam, Boolean valor, String persistence_unit) {
        try {
            List<?> objects = getQuery(strHQL, persistence_unit).setParameter(strParam, valor).getResultList();
            this.closeSession(persistence_unit);
            return objects;
        } catch (Exception e) {
            this.closeSession(persistence_unit);
            return null;
        }
    }

    public Object getObject(String strHQL, String persistence_unit) {
        try {
            List<?> objects = getQuery(strHQL, persistence_unit).getResultList();
            this.closeSession(persistence_unit);
            return objects.get(0);
        } catch (Exception e) {
            this.closeSession(persistence_unit);
            return null;
        }
    }

    public Object getObject(String strHQL, String strParam, Object valor, String persistence_unit) {
        try {
            List<?> objects = getQuery(strHQL, persistence_unit).setParameter(strParam, valor).getResultList();
            this.closeSession(persistence_unit);
            return objects.get(0);
        } catch (Exception ex) {
            this.closeSession(persistence_unit);
            return null;
        }
    }

    public Object getObject(String strHQL, String[] strParam, String[] valor, String persistence_unit) {
        try {
            Object temp;
            Query query = getQuery(strHQL, persistence_unit);
            setParameters(query, strParam, valor);
            Object objects = query.getSingleResult();
            this.closeSession(persistence_unit);
            temp = objects;
            return temp;
        } catch (Exception ex) {
            this.closeSession(persistence_unit);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private Query getQuery(String strHQL, String persistence_unit) {
        try {
            EntityManager session = this.getSession(persistence_unit);
            session.getTransaction().begin();
            return session.createQuery(strHQL);
        } catch (Exception e) {
            return null;
        }
    }

    private Query getNamedQuery(Class classType, String namedQuery, String persistence_unit) {
        try {
            EntityManager session = this.getSession(persistence_unit);
            session.getTransaction().begin();
            return session.createNamedQuery(namedQuery, classType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Query setParameters(Query query, String[] strParam, Object[] valor) {
        try {
            if (strParam != null) {
                for (int i = 0; i < strParam.length; i++) {
                    if (strParam[i] != null) {
                        query.setParameter(strParam[i], valor[i]);
                    }
                }
            }
            return query;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
