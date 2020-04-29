package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {
	
	Map<Integer, Nerc> idNerc;
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();
		idNerc = new TreeMap<Integer, Nerc>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
				idNerc.put(res.getInt("id"), n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	//convertire le date res.getDate("datecolumn").toLocalDate()
	public List<PowerOutage> getBlackout(Nerc nerc) {

		String sql = "SELECT p.id, date_event_began, date_event_finished, n.id, n.value, customers_affected FROM poweroutages AS p, nerc AS n WHERE p.nerc_id=n.id AND n.value=? ORDER BY date_event_began Asc";
		List<PowerOutage> blackoutList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, nerc.getValue());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				PowerOutage p = new PowerOutage(rs.getInt("id"), idNerc.get(rs.getInt("n.id")), rs.getTimestamp("date_event_began").toLocalDateTime(), rs.getTimestamp("date_event_finished").toLocalDateTime(), rs.getInt("customers_affected"));
				blackoutList.add(p);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return blackoutList;
	}
}
