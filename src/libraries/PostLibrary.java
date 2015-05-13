package libraries;

public class PostLibrary {
	public static String getParentCategoryPostByURL(String url){
		String strArr [] = url.split("/");
		String str = "";
		for(int i = 2; i<strArr.length-1; i++){
			if(i<strArr.length-2){
				str+=strArr[i]+"/";
			}else{
				str+=strArr[i];
			}
		}
		return str;
	}
	public static void main(String [] args){
		System.out.println(PostLibrary.getParentCategoryPostByURL("/WebAppDemo/giai-tri/games/Lorem-Ipsum-chi-don-gian-la-mot-doan-van-ban-gia.html"));
	}
}
