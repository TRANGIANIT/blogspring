package libraries;

import java.util.ArrayList;

import models.POJO.Category;

public class CategoryLibrary {
	private static int getSubCate(int catID, ArrayList<Category> l) {
		int numSubCate = 0;
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getParentID() == catID) {
				numSubCate++;
			}
		}
		return numSubCate;
	}

	public static String getCategoryNameByID(int id, ArrayList<Category> l) {
		String cateName = null;
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getCatID() == id) {
				cateName = l.get(i).getCatName();
			}
		}
		return cateName;
	}

	private static String str = "";

	private static void getCategoriesList(int t, ArrayList<Category> l,
			String url) {
		if (getSubCate(t, l) > 0) {
			str += "<ul>";
			if (getCategoryNameByID(t, l) != null) {

				url += Utilities.toUrlFriendly(getCategoryNameByID(t, l)
						.toLowerCase()) + "/";
			} else {
				url += "/category/";
			}
		}
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getParentID() == t) {
				str += "<li><a href='"
						+ url
						+ Utilities.toUrlFriendly(l.get(i).getCatName()
								.toLowerCase()) + "'>";
				str += l.get(i).getCatName() + "</a>";
				getCategoriesList(l.get(i).getCatID(), l, url);
				str += "</li>";
			}
		}
		if (getSubCate(t, l) > 0) {
			str += "</ul>";
		}
	}

	public static String printerCate(ArrayList<Category> l, String url) {
		str = "";
		getCategoriesList(0, l, url);
		return str;
	}
	
	public static String getCategorySlugByURL(String url){
		String cate=null;
		String s[] =url.split("/");
		if(s.length>0){
			cate = s[s.length-1];
		}
		return cate;
	}
	
	 public static void main(String [] args){
		 String str = "http://localhost:8080/WebAppDemo/category/giai-tri/games/action";
		 System.out.println(CategoryLibrary.getCategorySlugByURL(str));
	 }
}
