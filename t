[1mdiff --cc WebRoot/WEB-INF/classes/com/virtualcloset/servlet/DeleteData.class[m
[1mindex bda0b8f,332a73a..0000000[m
Binary files differ
[1mdiff --git a/WebRoot/WEB-INF/classes/com/virtualcloset/servlet/FetchIDS.class b/WebRoot/WEB-INF/classes/com/virtualcloset/servlet/FetchIDS.class[m
[1mindex 9d944d0..c55817b 100644[m
Binary files a/WebRoot/WEB-INF/classes/com/virtualcloset/servlet/FetchIDS.class and b/WebRoot/WEB-INF/classes/com/virtualcloset/servlet/FetchIDS.class differ
[1mdiff --git a/src/com/virtualcloset/servlet/FetchIDS.java b/src/com/virtualcloset/servlet/FetchIDS.java[m
[1mindex 6fb2ca0..5680b8f 100644[m
[1m--- a/src/com/virtualcloset/servlet/FetchIDS.java[m
[1m+++ b/src/com/virtualcloset/servlet/FetchIDS.java[m
[36m@@ -36,7 +36,9 @@[m [mpublic class FetchIDS extends HttpServlet {[m
 			if(idsType.equals("imageids")){[m
 				ImageBean image = new ImageBean(jobj);[m
 				for(ImageBean im : image.getUserAllImage()){[m
[31m-					idsArray.put(im.imageId);[m
[32m+[m					[32mif(!im.afterDeal.isEmpty()){[m
[32m+[m						[32midsArray.put(im.imageId);[m
[32m+[m					[32m}[m
 				}[m
 				jobj.put("message", 0);[m
 				jobj.put("image_ids", idsArray);[m
