package acb;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class Modelo {
	private EntityManager em = null;

	public Modelo() {
		try {
			
			em = Persistence.createEntityManagerFactory("ACB").createEntityManager();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	public void cerrar() {
		em.close();
	}

	public void mostrarPartidos() {
		try {
			// TODO Auto-generated method stub
			Query consulta = em.createQuery("from Partido");
			
			List<Partido> r = consulta.getResultList();
			for(Partido p: r) {
				p.mostrar();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	public Partido obtenerPartido(Partido pSel) {
		// TODO Auto-generated method stub
		return em.find(Partido.class, pSel.getCodigo());
	}

	public void mostrarTiposAccion() {
		// TODO Auto-generated method stub
		try {
			// TODO Auto-generated method stub
			Query consulta = em.createQuery("from TipoAccion");
			
			List<TipoAccion> r = consulta.getResultList();
			for(TipoAccion t: r) {
				t.mostrar();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public TipoAccion obtenerTipoAccion(TipoAccion tipoA) {
		// TODO Auto-generated method stub
		return em.find(TipoAccion.class, tipoA.getTipo());
	}

	public void mostrarJugadores(Partido pSel) {
		// TODO Auto-generated method stub
		try {
			// TODO Auto-generated method stub
			Query consulta = em.createQuery("from Jugador "
					+ "where equipo.nombre = :eLocal or equipo = :eVisi");
			consulta.setParameter("eLocal", pSel.getLocal().getNombre());
			consulta.setParameter("eVisi", pSel.getVisitante());
			
			List<Jugador> r = consulta.getResultList();
			for(Jugador j: r) {
				j.mostrar();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public Jugador obtenerJugador(Jugador j) {
		// TODO Auto-generated method stub
		return em.find(Jugador.class, j.getCodigo());
	}

	public boolean registrarAccion(Partido pSel, Jugador j, TipoAccion tipoA) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		EntityTransaction t = null;
		try {
			t= em.getTransaction();
			t.begin();
			
			Accion a = new Accion(pSel, tipoA, j, false);
			em.persist(a);
			t.commit();
			resultado= true;
			em.clear();
		}
		catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			e.printStackTrace();
		}
		return resultado;
	}

	public boolean anularAccion(Accion a) {
		// TODO Auto-generated method stub
		boolean resultado=false;
		
		EntityTransaction t = null;
		try {
			t= em.getTransaction();
			t.begin();
			
			a.setAnulada(true);
			
			t.commit();
			resultado= true;
			em.clear();
		}
		catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			e.printStackTrace();
		}
		
		return resultado;
	}

	public boolean borrarPartido(Partido p) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		/* Hay dos formas de hacerlo
		 1) sin consulta, borrando el objeto del em
		 2)con consulta
		 
		 Forma 1)
		 
		EntityTransaction t = null;
		try {
			t= em.getTransaction();
			t.begin();
			em.remove(p);
			t.commit();
			em.clear();
			resultado = true;
			
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			e.printStackTrace();
		}
		
		Forma 2)*/
		EntityTransaction t = null;
		try {
			t= em.getTransaction();
			t.begin();
			Query consulta = em.createQuery("delete from Partido "
					+ "where codigo = :pCodigo");
			consulta.setParameter("pCodigo", p.getCodigo());
			int r = consulta.executeUpdate();
			if(r==1) {
				resultado = true;
			}
			t.commit();
			em.clear();
			
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			e.printStackTrace();
		}
		
		return resultado;
	}

	public void mostrarEstadistica(Partido pSel) {
		// TODO Auto-generated method stub
		
		try {
			Query consulta = em.createNativeQuery("call obtenerEstadistica(:partido)");
			consulta.setParameter("partido", pSel.getCodigo());
			List<Object[]> r = consulta.getResultList();
			for(Object[] d: r) {
				
				System.out.println("Puntos Local:" + (BigInteger)d[0] + 
						"\tPuntos visitante:" + (BigInteger)d[1] +  
						"\tC1L:" + (Integer)d[2] + 
						"\tC1V:" + (Integer)d[3] + 
						"\tC2L:" + (Integer)d[4] + 
						"\tC2V:" + (Integer)d[5] + 
						"\tC3L:" + (Integer)d[6] + 
						"\tC3V:" + (Integer)d[7]
						);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
}
