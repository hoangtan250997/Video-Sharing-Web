package edu.poly.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import edu.poly.domain.FavoriteReport;
import edu.poly.domain.FavoriteUserReport;
import edu.poly.model.Favorite;

public class FavoriteDao extends AbstractEntityDao<Favorite> {

	public FavoriteDao() {
		super(Favorite.class);
	}
	
	public List<FavoriteUserReport> reportFavoriteUserbyVideo(String videoId){
		String jpql = " select new edu.poly.domain.FavoriteUserReport(f.user.username, f.user.fullname, "
				+ "f.user.email,f.likedDate) from Favorite f where f.video.videoId = :videoId";
		EntityManager em = JpaUtils.getEntityManager();
		List<FavoriteUserReport> list = null;
	try {
		TypedQuery<FavoriteUserReport> query = em.createQuery(jpql,FavoriteUserReport.class);
		System.out.println("Dao5");
		query.setParameter("videoId", videoId);
		list = query.getResultList();
		System.out.println("Dao6");
		System.out.print(list.size());
	} finally {em.close();}
	
	return list;
}
	
	public List<FavoriteReport> reportFavoritesByVideos(){
		System.out.println("Dao1");
		String jpql = "select new edu.poly.domain.FavoriteReport(f.video.title, count(f), min(f.likedDate),max(f.likedDate)) "
				+ " from Favorite f group by f.video.title ";
		System.out.println("Dao2");
		EntityManager em = JpaUtils.getEntityManager();
		System.out.println("Dao3");
		List<FavoriteReport> list = null;
		System.out.println("Dao4");

		try {
			TypedQuery<FavoriteReport> query = em.createQuery(jpql,FavoriteReport.class);
			System.out.println("Dao5");
			list = query.getResultList();
			System.out.println("Dao6");
			System.out.print(list.size());
		} finally {em.close();}
		
		
		return list;
	}

}
