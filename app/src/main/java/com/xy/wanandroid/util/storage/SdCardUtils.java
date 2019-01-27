package com.xy.wanandroid.util.storage;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SdCardUtils {
	private static final String TAG = "ym";
	/**
	 * 判断sdCard是否能用*/
	public boolean isSafe() {
		return getSdFile().equals(Environment.MEDIA_MOUNTED);
	}
	/**
	 * 获取sdCard内存总大小*/
	public String getTotalSize(Context context) {
		//获取根目录
		File path = getSdFile();
		// 获取指定目录下的内存存储状态
		StatFs stat = new StatFs(path.getPath());
		//获取每个扇区的大小
		long blockSize = stat.getBlockSize();
		//获取扇区的数量
		long totalBlocks = stat.getBlockCount();
		// 总空间 = 扇区的总数 * 扇区的大小
		long totalSize = blockSize * totalBlocks;
		String size = Formatter.formatFileSize(context, totalSize);
		Log.i("ym", "总空间 = " + size);
		return size;
	}
	/**
	 * 获取sdCard可用内存大小*/
	
public String getAvailableSize(Context context){
		
		// 获取SD卡根目录
		File path = getSdFile();
		// 获取指定目录下的内存存储状态
		StatFs stat = new StatFs(path.getPath());
		// 获取单个扇区的大小
		long blockSize = stat.getBlockSize();
		// 获取可以使用的扇区数量
		long availableBlocks = stat.getAvailableBlocks();
		// 可用空间 = 扇区的大小 + 可用的扇区
		long availableSize = blockSize * availableBlocks;
		// 格式化文件大小的格式
		String size = Formatter.formatFileSize(context, availableSize);
		Log.i("sdcard", "总空间 = " + size);		
		return size;
		
	}
	/**
	 * 获取sd卡根目录字符串的路径*/
	
	public String getSdPath () {
		return getSdFile().getAbsolutePath();
	}
	
	/**
	 * 获取sd卡*/
	public String getInnerSDCardPath() {
		return Environment.getExternalStorageDirectory().getPath();
	}
	public File getSdFile(){
		return Environment.getExternalStorageDirectory();
	}
	
	/**
	 *读取指定文件中的数据，将数据读取为byte[]类型 */
	public byte[] getDataFromFile(String filePath) {
		
		BufferedInputStream bis = null;
		FileInputStream fis = null;
		ByteArrayOutputStream baos = null;
		try {
			fis = new FileInputStream(filePath);
			bis = new BufferedInputStream(fis);
			int len = -1;
			byte[] buffer = new byte[1024];
			while ((len = bis.read(buffer))!=-1) {
				baos.write(buffer, 0, len);
			}
			return baos.toByteArray();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (bis!=null) {
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (baos!=null) {
				try {
					baos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 向指定路径中写入指定数据
	 * */
	public void saveFile(String path,byte[] data,String fileName) {

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		BufferedOutputStream bos = null;
		Log.e(TAG, "**********************:  "+(path+File.separator+fileName) );
		try {
			bos = new BufferedOutputStream(new FileOutputStream(path+File.separator+fileName));
			bos.write(data);
			bos.flush();
			Log.i("ym", "**********************: 数据存完");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "**********************:  "+e.getMessage() );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "**********************:  "+e.getMessage() );
		}finally{
			if (bos!=null) {
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 复制文件*/
	public void copyFile(String fromPath,String saveAsFileName,String toPath) {
		byte[] data = getDataFromFile(fromPath);
		saveFile(toPath, data, saveAsFileName);
	}
	
	/**
	 * 删除文件*/
	public void deleteFile(String filePath) {
		File file = new File(filePath);
		file.delete();
	}
	/**
	 * 剪切文件*/
	public void cutFile(String fromPath,String saveAsFileName,String toPath) {
		copyFile(fromPath, saveAsFileName, toPath);
		deleteFile(fromPath);
	}
	//获取指定文件大小
	public long getFolderSize(File file){
		long size = 0;
		try {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++)
			{
				if (fileList[i].isDirectory())
				{
					size = size + getFolderSize(fileList[i]);

				}else{
					size = size + fileList[i].length();

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return size/1048576;
		return size;
	}

	public void test(String saveAsFileName,String toPath) {
		String str = "我的天那你是猪吗哈哈哈哈我的天那你是猪吗哈哈哈哈我的天那你是猪吗哈哈哈哈我的天那你是猪吗哈哈哈哈我的天那你是猪吗哈哈哈哈我的天那你是猪吗哈哈哈哈😄😄啊哈😄啊哈好";
		byte[] data = str.getBytes();
		saveFile(toPath, data, saveAsFileName);
	}

//	{"result":{"mix_1":{"error_code":22000,"result":[{"tip_type":0,"title":"新歌抢鲜听","desc":"千千音乐1月速递","pic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_1548243266f7c38765a8b3681ce7e086942f28190d.jpg","type_id":"565468058","program_title":"","type":0,"program_periods":"","widepic":"","author":"千千音乐1月速递"},{"tip_type":0,"title":"大哥你好吗（电影《飞驰人生》推广曲）","desc":"腾格尔,沈腾","pic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_1548427572267e7c8319e7d6b824a98ebed3f768cc.jpg","type_id":"612170858","program_title":"","type":2,"program_periods":"","widepic":"","author":"腾格尔,沈腾"},{"tip_type":0,"title":"半壶纱","desc":"刘珂矣","pic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_15482967910baddd7e0a31383d57ea401d2d90f0c4.jpg","type_id":"612135962","program_title":"","type":2,"program_periods":"","widepic":"","author":"刘珂矣"},{"tip_type":0,"title":"アニメ「怪獣娘(黒)～ウルトラ怪獣擬人化計画～」OP主題歌「東奔西走行進曲」","desc":"BLACK STARS(ブラック指令・ペガッサ星人・シルバーブルーメ・ノーバ)","pic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_15483308573e87a04e7c672217469f323c096fb57e.jpg","type_id":"612155797","program_title":"","type":2,"program_periods":"","widepic":"","author":"BLACK STARS(ブラック指令・ペガッサ星人・シルバーブルーメ・ノーバ)"},{"tip_type":0,"title":"君がいるなら","desc":"スカート","pic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_1548242353fbbdcc57e32e47c11878868864502461.jpg","type_id":"612135457","program_title":"","type":2,"program_periods":"","widepic":"","author":"スカート"},{"tip_type":0,"title":"Trappin","desc":"Don·Jessie","pic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_154833096185be8c873badb32575ddf06b426480e0.jpg","type_id":"612155947","program_title":"","type":2,"program_periods":"","widepic":"","author":"Don·Jessie"}]},"mod_29":{"error_code":22000,"result":[{"tip_type":0,"title":"U榜","desc":"","pic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_1543766650b79c718c99baa879cea6cc91b97c1baf.jpg","type_id":"http:\/\/music.baidu.com\/cms\/webview\/celebrity_list\/v1?_topbar=transparent","program_title":"","type":4,"program_periods":"","widepic":"","author":""}]},"mix_22":{"error_code":22000,"result":[{"tip_type":0,"title":"你给不了","desc":"多小逗","pic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_1547186028067923a9a39f57f056ebd39a374b01dc.jpg","type_id":"610387958","program_title":"","type":2,"program_periods":"","widepic":"","author":"多小逗"},{"tip_type":0,"title":"皎皎","desc":"薇小薇","pic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_154718514385e29f6eaf84a7473d80436d3a61aead.jpg","type_id":"610475061","program_title":"","type":2,"program_periods":"","widepic":"","author":"薇小薇"},{"tip_type":0,"title":"请原谅我","desc":"刘大洋Leo","pic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_1533357114cf7889676532c7ac001a0d5072890d8a.jpg","type_id":"594759511","program_title":"","type":2,"program_periods":"","widepic":"","author":"刘大洋Leo"}]},"entry":{"error_code":22000,"result":[{"day":"","title":"歌手","icon":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_14639875926652ed7c4988517cab87526f15d8f359.jpg","jump":"2"},{"day":"","title":"歌曲分类","icon":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_146398764316d87d01865b91f90a598777b1569fdf.jpg","jump":"1"},{"day":"26","title":"今日推荐歌曲","icon":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_1463987629793f4361391282bde14d9b19156cfac3.jpg","jump":"0"}]},"scene":{"result":{"action":[{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/9d82d158ccbf6c811f661c18ba3eb13532fa40e8.jpg","scene_name":"清晨","scene_id":"5","icon_android":"http:\/\/hiphotos.qianqic\/item\/f636afc379310a55e4d177f7b04543a98226103f.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""},{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/5366d0160924ab18426ed0c733fae6cd7a890bc1.jpg","scene_name":"运动","scene_id":"1","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/8326cffc1e178a82d9d1d1a3f103738da877e8c0.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""},{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/6159252dd42a28340be30dfb5db5c9ea14cebffc.jpg","scene_name":"蹲马桶","scene_id":"22","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/11385343fbf2b2112cb92e4dcd8065380cd78e1a.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""},{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/5bafa40f4bfbfbed7770128f7ef0f736afc31f10.jpg","scene_name":"做家务","scene_id":"31","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/960a304e251f95cad85a0fb5ce177f3e67095201.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""},{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/7acb0a46f21fbe09590cfbe46d600c338644adeb.jpg","scene_name":"懒懒地醒来","scene_id":"20","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/5fdf8db1cb134954f23a4367514e9258d1094a89.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""},{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/908fa0ec08fa513d01695d2e3b6d55fbb3fbd97a.jpg","scene_name":"聚会","scene_id":"2","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/f31fbe096b63f624cf5490008044ebf81a4ca3ac.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""}],"emotion":[{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/a1ec08fa513d26974c932aad53fbb2fb4216d8f9.jpg","scene_name":"开心","scene_id":"38","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/f703738da9773912e4621a62ff198618367ae269.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""},{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/7acb0a46f21fbe094353e1e46d600c338744ad34.jpg","scene_name":"轻松","scene_id":"40","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/bf096b63f6246b609bc02d77ecf81a4c500fa2e3.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""},{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/3bf33a87e950352a854306f85543fbf2b2118b1b.jpg","scene_name":"激动","scene_id":"34","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/5882b2b7d0a20cf4f1ac61dc71094b36acaf99f1.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""}],"operation":[{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/faedab64034f78f04fd1977e7f310a55b2191c60.jpg","scene_name":"古风","scene_id":"157","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/2cf5e0fe9925bc313c5e079a59df8db1cb1370b4.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""},{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/cb8065380cd79123bb1999f9ab345982b3b78045.jpg","scene_name":"小清新","scene_id":"158","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/80cb39dbb6fd526672003a16ac18972bd407368c.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""},{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/dbb44aed2e738bd461c6bebca78b87d6267ff95c.jpg","scene_name":"情人节","scene_id":"155","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/63d0f703918fa0ecfe25630f219759ee3c6ddbd6.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""},{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/c8ea15ce36d3d539961d09c13d87e950352ab08b.jpg","scene_name":"舒缓","scene_id":"159","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/0824ab18972bd40792add5ff7c899e510fb3094d.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""},{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/bd3eb13533fa828bd727916efb1f4134970a5a32.jpg","scene_name":"新歌抢鲜听","scene_id":"33","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/9213b07eca8065382359a31190dda144ad348204.jpg","bgpic_ios":"","scene_desc":"网罗全球最新歌曲","scene_model":"1","bgpic_android":""},{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/18d8bc3eb13533faf2ba9fa3aed3fd1f41345b2a.jpg","scene_name":"网络歌曲","scene_id":"156","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/9922720e0cf3d7ca73313874f51fbe096a63a9e8.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""},{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/d01373f082025aaff566d3a4fcedab64034f1a0c.jpg","scene_name":"热歌","scene_id":"162","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/838ba61ea8d3fd1f55b20f55374e251f95ca5f21.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""},{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/c2fdfc039245d6884448bf29a2c27d1ed31b24f0.jpg","scene_name":"经典老歌","scene_id":"161","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/d058ccbf6c81800a68b98f0cb63533fa828b4757.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""},{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/4a36acaf2edda3cc469cb5ad07e93901203f92c6.jpg","scene_name":"校园歌曲","scene_id":"160","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/a71ea8d3fd1f4134ed57037c221f95cad1c85e4b.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""}],"other":[{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/b2de9c82d158ccbfe74098ca1fd8bc3eb0354145.jpg","scene_name":"国语","scene_id":"42","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/d439b6003af33a87423e9e19c15c10385343b566.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""},{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/377adab44aed2e7364d7a8dc8101a18b87d6fa00.jpg","scene_name":"2000年代","scene_id":"71","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/aa64034f78f0f736ec9dd4020d55b319ebc41355.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""},{"icon_ios":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/b3b7d0a20cf431ad9bb26ac94d36acaf2fdd98d9.jpg","scene_name":"流行","scene_id":"48","icon_android":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/a50f4bfbfbedab6443ffd5c3f036afc379311e27.jpg","bgpic_ios":"","scene_desc":"","scene_model":"1","bgpic_android":""}]},"error_code":22000,"config":[{"color_other":"","play_color":"","scene_version":0,"desc":"","end_time":0,"scene_color":"","start_time":0,"bgpic":"","bgpic_special":"","button_color":""}]},"show_list":{"error_code":22000,"result":[{"type":"learn","picture_iphone6":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_2bc81ed7070a7db830a9c8309080d2f4.jpg","picture":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_2b983ddd40a431e1b66a6cb290f0a770.jpg","web_url":"http:\/\/music.baidu.com\/cms\/webview\/ktv_activity\/20170112\/"}]},"recsong":{"error_code":22000,"result":[{"resource_type_ext":"0","distribution":"0000","has_filmtv":"0","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/7323c3fc208ad85039ffa3ffacba3207\/603897281\/603897281.jpg@s_1,w_500,h_500","album_id":"242086917","biaoshi":"lossless,perm-3","si_proxycompany":"北京百慕文化发展有限公司","versions":"影视原声","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","info":"电视剧《神犬小七》电视剧主题曲","copy_type":"1","album_title":"不要忘记我爱你","method":"4","title":"不要忘记我爱你","song_id":"242086907","author":"张碧晨","del_status":"0","korean_bb_song":"0","has_mv_mobile":"1"},{"resource":"111","has_filmtv":"0","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/316aee9f76cd1b62540e7f80a15c8757\/602542891\/602542891.jpg@s_1,w_500,h_500","album_id":"602542890","biaoshi":"lossless,perm-1","si_proxycompany":"代亚（上海）文化传媒中心","versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"0|0\"}","info":"","copy_type":"1","album_title":"我已经爱上你","method":"4","title":"我已经爱上你","song_id":"602542893","author":"颜妹,陌小柒","del_status":"0","korean_bb_song":"0","has_mv_mobile":"0"},{"resource_type_ext":"2","distribution":"000000000","has_filmtv":"0","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/music\/04AE3D8247BCD9C35D180E5CE2908C8C\/252191349\/252191349.jpg@s_0,w_500","album_id":"72868","biaoshi":"vip,lossless,perm-3","si_proxycompany":"华宇世博音乐文化（北京）有限公司-力行网际","versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","info":"","copy_type":"1","album_title":"狼的专辑","method":"4","title":"狼","song_id":"1404724","author":"齐秦","del_status":"0","korean_bb_song":"0","has_mv_mobile":"1"},{"resource_type_ext":"0","distribution":"00","has_filmtv":"0","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/ee6432f2ef6420d0632cb897f3f0f7fb\/566871160\/566871160.jpg@s_1,w_500,h_500","album_id":"538777110","biaoshi":"lossless,perm-3","si_proxycompany":"华宇世博音乐文化（北京）有限公司-海蝶音乐","versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","info":"","copy_type":"1","album_title":"渡 The Crossing","method":"4","title":"动物世界","song_id":"539843843","author":"薛之谦","del_status":"0","korean_bb_song":"0","has_mv_mobile":"1"},{"resource_type_ext":"0","distribution":"0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,1111111111,1111111111,0000000000","has_filmtv":"1","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/afbf57a0faf49ba3cc64ad74d92c50f4\/66135\/66135.jpg","album_id":"66135","biaoshi":"lossless,perm-3","si_proxycompany":"滚石国际音乐股份有限公司","versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","info":"","copy_type":"1","album_title":"听健","method":"4","title":"难念的经","song_id":"246197","author":"周华健","del_status":"0","korean_bb_song":"0","has_mv_mobile":"1"},{"resource_type_ext":"0","distribution":"0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,1111111111,0000000000","has_filmtv":"1","learn":"1","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/fa2da19b8344d2848a66d8998e5e66e1\/70727\/70727.png@s_1,w_500,h_500","album_id":"70727","biaoshi":"lossless,perm-3","si_proxycompany":"滚石国际音乐股份有限公司","versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","info":"电影《决死主战场梦醒时分》主题曲,电影《决死主战场》主题曲,电影《梦醒时分》主题曲","copy_type":"1","album_title":"给淑桦的一封信","method":"4","title":"梦醒时分","song_id":"2043361","author":"陈淑桦","del_status":"0","korean_bb_song":"0","has_mv_mobile":"1"},{"resource_type_ext":"2","distribution":"0000","has_filmtv":"0","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/d59cab8d47b4ae5cd500cbb67de9cc5c\/276867491\/276867491.jpg@s_1,w_500,h_500","album_id":"276867491","biaoshi":"vip,lossless,perm-3","si_proxycompany":"北京新奥视讯国际文化传媒有限公司","versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","info":"","copy_type":"1","album_title":"刚好遇见你","method":"4","title":"清明上河图","song_id":"540103544","author":"李玉� /556405730\/556405730.jpg@s_1,w_500,h_500","album_id":"276709020","biaoshi":"vip,lossless,perm-1","si_proxycompany":"华宇世博音乐文化（北 京）有限公司-太合麦田","versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","info":"","copy_type":"1","album_title":"青春无悔 ","method":"4","title":"冬季校园","song_id":"1260508","author":"小柯","del_status":"0","korean_bb_song":"0","has_mv_mobile":"0"},{"resource_type_ext":"2","distribution":"0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000","has_filmtv":"1","learn":"1","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/a9e16fad3096945f29ecca722e2630c0\/568122106\/568122106.jpg@s_1,w_500,h_500","album_id":"2496539","biaoshi":"vip,lossless,perm-3","si_proxycompany":"华宇世博音乐文化（北京）有限公司-海蝶音乐","versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","info":"电视剧《爱情睡醒了》插曲","copy_type":"1","album_title":"她说 概念自选辑","method":"4","title":"只对你有感觉","song_id":"2496515","author":"林俊杰","del_status":"0","korean_bb_song":"0","has_mv_mobile":"1"},{"resource_type_ext":"2","distribution":"0000","has_filmtv":"0","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/fc58646226f7d92346c5515d7c56d8dd\/611607392\/611607392.jpg@s_1,w_500,h_500","album_id":"611607356","biaoshi":"vip,first,lossless,perm-1","si_proxycompany":"华宇世博音乐文化（北京）有限公司-普通代理","versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","info":"","copy_type":"1","album_title":"原来你有男朋友","method":"4","title":"原来你有男朋友","song_id":"611607358","author":"刘维","del_status":"0","korean_bb_song":"0","has_mv_mobile":"0"},{"resource_type_ext":"0","distribution":"0000","has_filmtv":"0","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/129551203\/129551203.png@s_0,w_500","album_id":"129552065","biaoshi":"perm-1","si_proxycompany":"北京乐享天承文化传媒有限公司","versions":"混音","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","info":"","copy_type":"1","album_title":"小苹果（新年Remix版）","method":"4","title":"小苹果（新年Remix版）","song_id":"129552034","author":"筷子兄弟","del_status":"0","korean_bb_song":"0","has_mv_mobile":"0"},{"resource_type_ext":"0","distribution":"0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,1111111111,1111111111,0000000000","has_filmtv":"0","learn":"1","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/0d33e6a5a83f8069b2f297d749f60b9c\/569002139\/569002139.jpg@s_1,w_500,h_500","album_id":"123539942","biaoshi":"lossless,perm-1","si_proxycompany":"安徽汉马文化传媒有限公司","versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","info":"","copy_type":"1","album_title":"梦醉荷塘","method":"4","title":"梦醉荷塘","song_id":"123539943","author":"高安,云菲菲","del_status":"0","korean_bb_song":"0","has_mv_mobile":"0"},{"resource_type_ext":"0","distribution":"00000","has_filmtv":"0","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/b8c0502c224c03076117cddcd39df90d\/603437062\/603437062.png@s_1,w_500,h_500","album_id":"603437061","biaoshi":"lossless,perm-1","si_proxycompany":"代亚（上海）文化传媒中心","versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","info":"","copy_type":"1","album_title":"确认过眼神遇见对的人","method":"4","title":"病变","song_id":"603437074","author":"王梓天","del_status":"0","korean_bb_song":"0","has_mv_mobile":"0"},{"resource_type_ext":"0","dist":"qwqw","copy_type":"1","album_title":"马头琴恋曲","method":"4","title":"马头琴恋曲","song_id":"242549690","author":"乌兰图雅,额尔古纳乐队","del_status":"0","korean_bb_song":"0","has_mv_mobile":"0"},{"resource_type_ext":"2","distribution":"0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000","has_filmtv":"1","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/fa5f96247ad91be8d30693dfbbdd94ac\/611867285\/611867285.jpg@s_1,w_500,h_500","album_id":"104635","biaoshi":"vip,perm-3","si_proxycompany":"北京龙乐世纪文化传媒有限公司","versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","info":"电视剧《孝庄秘史》主题曲","copy_type":"1","album_title":"风云2","method":"4","title":"你","song_id":"1051979","author":"屠洪刚","del_status":"0","korean_bb_song":"0","has_mv_mobile":"1"},{"resource_type_ext":"0","distribution":"00000","has_filmtv":"0","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/8079fc1abafdf372e4710556384a054c\/266685300\/266685300.jpg@s_0,w_500","album_id":"266685299","biaoshi":"lossless,perm-1","si_proxycompany":"劳爱思（北京）文化传媒有限公司","versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","info":"","copy_type":"1","album_title":"我最爱的人","method":"4","title":"我最爱的人","song_id":"266685655","author":"洪川","del_status":"0","korean_bb_song":"0","has_mv_mobile":"0"},{"resource_type_ext":"2","distribution":"00000","has_filmtv":"0","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/1fed21ca2c1fffb3afd331e4f2b094e4\/602713593\/602713593.jpg@s_1,w_500,h_500","album_id":"602713592","biaoshi":"vip,lossless,perm-3","si_proxycompany":"华宇世博音乐文化（北京）有限公司-普通代理","versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","info":"","copy_type":"1","album_title":"月弯弯","method":"4","title":"月弯弯","song_id":"602713595","author":"陈洁仪","del_status":"0","korean_bb_song":"0","has_mv_mobile":"1"},{"resource_type_ext":"0","distribution":"000000","has_filmtv":"0","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/c7bfbbe1908abdeaeec414b548edb597\/612046100\/612046100.jpg","album_id":"606608846","biaoshi":"perm-1","si_proxycompany":"北京乐诗文化传播有限公司","versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","info":"","copy_type":"1","album_title":"往后余生（2018版）","method":"4","title":"往后余生（2018版）","song_id":"606608848","author":"萧忆情Alex","del_status":"0","korean_bb_song":"0","has_mv_mobile":"0"},{"resource_type_ext":"0","distribution":"0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,1111111111,1111111111,0000000000","has_filmtv":"0","learn":"1","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/5b76311f917dae24106137a5363b4c22\/193306\/193306.jpg@s_1,w_500,h_500","album_id":"193306","biaoshi":"lossless,perm-3","si_proxycompany":"滚石国际音乐股份有限公司","versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","info":"","copy_type":"1","album_title":"我要说I SAY","method":"4","title":"坚强的理由","song_id":"702789","author":"莫文蔚,伍佰","del_status":"0","korean_bb_song":"0","has_mv_mobile":"1"},{"resource_type_ext":"0","distribution":"0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,1111111111,1111111111,0000000000","has_filmtv":"1","learn":"1","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/90392fb17371a44e39097fd00846ef97\/66087\/66087.jpg@s_1,w_500,h_500","album_id":"66087","biaoshi":"lossless,perm-3","si_proxycompany":"滚石国","ribution":"0000000,1111111111,1111111111,0000000000","has_filmtv":"0","learn":"1","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/d4718b25ee28b0e6d00dc4234ec07ed8\/341275\/341275.jpg@s_1,w_500,h_500","album_id":"341275","biaoshi":"vip,lossless,perm-3","si_proxycompany":"华宇世博音乐文化（北京）有限公司-亚神","versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","info":"","copy_type":"1","album_title":"Goodbye & Hello","method":"4","title":"空白格","song_id":"341541","author":"蔡健雅","del_status":"0","korean_bb_song":"0","has_mv_mobile":"1"},{"resource_type_ext":"2","distribution":"00000","has_filmtv":"0","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/86409428\/86409428.jpg@s_0,w_500","album_id":"612040340","biaoshi":"vip,first,lossless,perm-1","si_proxycompany":"杭州回声文化艺术策划有限公司","versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","info":"","copy_type":"1","album_title":"迷路","method":"4","title":"迷路","song_id":"612040343","author":"王蓉","del_status":"0","korean_bb_song":"0","has_mv_mobile":"0"},{"resource_type_ext":"2","distribution":"0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000","has_filmtv":"1","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/29bdde12df56b40fedd7a5d36a55f3ee\/596680117\/596680117.jpg@s_1,w_500,h_500","album_id":"14402726","biaoshi":"vip,lossless,perm-3","si_proxycompany":"华宇世博音乐文化（北 京）有限公司-太合麦田","versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","info":"","copy_type":"1","album_title":"我去2000年","method":"4","title":"那些花儿","song_id":"51907155","author":"朴树","del_status":"0","korean_bb_song":"0","has_mv_mobile":"1"},{"resource_type_ext":"0","distribution":"0000","has_filmtv":"0","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/bc104100f368e0add1076511afc5b23d\/579992784\/579992784.jpg@s_1,w_500,h_500","album_id":"258475887","biaoshi":"lossless,perm-1","si_proxycompany":"北京百慕文化发展有限公司","versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","info":"","copy_type":"1","album_title":"你不来我不老","method":"4","title":"你不来我不老 (对唱版)","song_id":"258475780","author":"高安,西单女孩","del_status":"0","korean_bb_song":"0","has_mv_mobile":"0"},{"resource_type_ext":"0","distribution":"0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,1111111111,1111111111,0000000000","has_filmtv":"0","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/124141102\/124141102.jpg@s_1,w_500,h_500","album_id":"124141130","biaoshi":"perm-1","si_proxycompany":"安徽省酝星文化传媒有限公司","versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","info":"","copy_type":"1","album_title":"家在草原","method":"4","title":"家在草原","song_id":"124141131","author":"乌兰图雅","del_status":"0","korean_bb_song":"0","has_mv_mobile":"0"},{"resource_type_ext":"0","distribution":"0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,1111111111,1111111111,0000000000","has_filmtv":"0","learn":"1","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/38d7db2bac258362f7f1e6409bf7112b\/68071\/68071.jpg","album_id":"68071","biaoshi":"lossless,perm-3","si_proxycompany":"滚石国际音乐股份有限公司","versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","info":"","copy_type":"1","album_title":"桃花朵朵开","method":"4","title":"桃花朵朵开","song_id":"328980","author":"阿牛","del_status":"0","korean_bb_song":"0","has_mv_mobile":"1"},{"resource_type_ext":"2","distribution":"867491.jpg@s_1,w_500,h_500","album_id":"276867491","biaoshi":"vip,lossless,perm-3","si_proxycompany":"北京新奥视讯国际文化传媒有限公司","versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","info":"","copy_type":"1","album_title":"刚好遇见你","method":"4","title":"新贵妃醉酒","song_id":"540103529","author":"李玉刚","del_status":"0","korean_bb_song":"0","has_mv_mobile":"1"},{"resource_type_ext":"0","distribution":"0000","has_filmtv":"0","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/cf12da59313f340424055369330464f7\/261977962\/261977962.jpg","album_id":"18155378","biaoshi":"lossless,perm-1","si_proxycompany":"北京新奥视讯国际文化传媒有限公司","versions":"混音","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","info":"","copy_type":"1","album_title":"DJ小可 ⑧王者再现","method":"4","title":"没办法忘记你(英文版)","song_id":"18159231","author":"DJ小可","del_status":"0","korean_bb_song":"0","has_mv_mobile":"0"},{"resource_type_ext":"2","distribution":"000000","has_filmtv":"0","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/f37a3c22e4885f25bbe81ac97ae8ca26\/611766801\/611766801.jpg@s_1,w_500,h_500","album_id":"34191191","biaoshi":"perm-1","si_proxycompany":"北京百慕文化发展有限公司","versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"0|0\"}","info":"","copy_type":"1","album_title":"如来云水月","method":"4","title":"大悲咒","song_id":"34190723","author":"敬善媛","del_status":"0","korean_bb_song":"0","has_mv_mobile":"0"},{"resource_type_ext":"2","distribution":"000000","has_filmtv":"0","learn":"0","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/a3ad3a08a246483dd08f533bdd037d03\/611662164\/611662164.jpg@s_1,w_500,h_500","album_id":"611661928","biaoshi":"vip,first,lossless,perm-1","si_proxycompany":"华宇世博音乐文化（北京）有限公司-普通代理","versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","info":"","copy_type":"1","album_title":"从心","method":"4","title":"从心","song_id":"611661930","author":"刘维","del_status":"0","korean_bb_song":"0","has_mv_mobile":"0"}]},"radio":{"error_code":22000,"result":[{"desc":"音乐推荐","itemid":"13496780","title":"有待咖啡","album_id":"13420707","type":"lebo","channelid":"11373552","pic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_019d01e1e994ba4e9d7890d9fc97f9eb.jpg"},{"desc":"音乐推荐","itemid":"13496374","title":"这样的罗大佑，你可能不太熟","album_id":"5945591","type":"lebo","channelid":"11373552","pic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_7894c83e731b9a8f4954fab18694db81.jpg"},{"desc":"都市情感","itemid":"13472541","title":"人生：十二年一个圆满或者缺憾","album_id":"12774274","type":"lebo","channelid":"11373553","pic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_01273c7cc2c630d44cbfd62d42a4c805.jpg"},{"desc":"段子笑话","itemid":"13497641","title":"胖友说，留言前100名让我带大家去吃小龙虾","album_id":"12380502","type":"lebo","channelid":"11373552","pic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_df5523f3cfd5a3266d669e085b5f1a5a.jpg"},{"desc":"音乐推荐","itemid":"13484227","title":"罗大佑丨光阴带走青春，留下了故事","album_id":"13384406","type":"lebo","channelid":"11373552","pic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_1e1f1cd57cd27d41a4d7eba22deb94ea.jpg"},{"desc":"教育","itemid":"13487126","title":"《小学生必背古诗词70+80》20：九月九日忆山东兄弟","album_id":"13483086","type":"lebo","channelid":"11373555","pic":"http:\/\/business.cdn.qianqian.com\/qianqiaic\/04a373d196f65490de2a3333bbbc781a.jpg","title":"醉爱的那些伤感老歌","tag":"经典,伤感,华语","collectnum":"","listid":"565472819","listenum":17922,"position":2},{"type":"gedan","pic":"http:\/\/musicugc.qianqian.com\/ugcdiy\/pic\/72711c62c99f8d74f92b3977b1da7ce8.jpg","title":"[ACG原声]熟悉的旋律，触动了谁的心弦","tag":"ACG,日语,游戏","collectnum":"","listid":"565842948","listenum":1356,"position":3},{"type":"gedan","pic":"http:\/\/musicugc.qianqian.com\/ugcdiy\/pic\/a9cef149ce31cc1f04a519096e20361b.jpg","title":"甜 的 燥 的 饶 舌 都 给 你","tag":"华语,说唱,兴奋","collectnum":"","listid":"565733388","listenum":672,"position":4},{"type":"gedan","pic":"http:\/\/musicugc.qianqian.com\/ugcdiy\/pic\/7b607fdb00332c6041fbb40a557180c5.jpg","title":"青春电影里的走心之歌","tag":"华语,影视原声,美好","collectnum":"","listid":"564830850","listenum":11808,"position":5},{"type":"gedan","pic":"http:\/\/musicugc.qianqian.com\/ugcdiy\/pic\/1ea8edb2b261407cb76ce37d9bf83f55.jpg","title":"放空也要放音乐","tag":"华语,放松,休息","collectnum":"","listid":"565570707","listenum":6618,"position":6}]},"focus":{"error_code":22000,"result":[{"randpic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_1548410203aff3138a8efb416acad10ab24b3aff1f.jpg","code":"http:\/\/music.taihe.com\/h5pc\/spec_detail?id=1550&columnid=89","mo_type":4,"type":6,"is_publish":"111111","randpic_iphone6":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_1548410203aff3138a8efb416acad10ab24b3aff1f.jpg","randpic_desc":""},{"randpic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_15481517375f2c1cdac1a685d50330f973cc6580ad.jpg","code":"http:\/\/music.taihe.com\/h5pc\/spec_detail?id=1549&columnid=87","mo_type":4,"type":6,"is_publish":"111111","randpic_iphone6":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_15481517375f2c1cdac1a685d50330f973cc6580ad.jpg","randpic_desc":""},{"randpic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_154803852965d64e14ff40ed6f742bf8e9ec601dd2.jpg","code":"http:\/\/music.taihe.com\/h5pc\/spec_detail?id=1545&columnid=96","mo_type":4,"type":6,"is_publish":"111111","randpic_iphone6":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_154803852965d64e14ff40ed6f742bf8e9ec601dd2.jpg","randpic_desc":""},{"randpic":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_1547466371f5a8864429456f49e904ab0430b8dc17.jpg","code":"https:\/\/music.taihe.com\/redstar?frs=qianqianbanner&hideplay=1&showminibar=0","mo_type":4,"type":6,"is_publish":"111111","randpic_iphone6":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_1547466371f5a8864429456f49e904ab0430b8dc17.jpg","randpic_desc":""}]},"new_song":{"error_code":22000,"result":{"pic_500":"http:\/\/hiphotos.qianqian.com\/ting\/pic\/item\/a50f4bfbfbedab64bcae572ef136afc378311e7b.jpg","listid":"5126","song_info":[{"song_id":"261812117","title":"二十四小时","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/261811991\/261811991.jpg@s_0,w_500","author":"陈坤,韩庚,大鹏,吴磊,尹正"},{"song_id":"74109283","title":"灵主不悔《画江湖之灵主》手游暨动漫主题曲","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/c0176928b966174a221d8afc151bcd29\/540418468\/540418468.jpg","author":"汪苏泷"},{"song_id":"261496612","title":"Protocole","pic_premium":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/07a830e962bbb4e58e29842f45d44b66\/261496583\/261496583.jpg@s_0,w_500","author":"Alpha Wann"}]}},"king":{"error_code":22000,"result":[{"pic_big":""},{"pic_big":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/415b9461efdf55d870e0b78110625ca0\/552847551\/552847551.jpg@s_1,w_150,h_150","title":"Papillon(delete)","author":"王嘉尔"},{"pic_big":"http:\/\/qukufile2.qianqian.com\/data2\/pic\/39d6703110744fa2ba12a0c7b48feef8\/552784968\/552784968.jpg@s_1,w_150,h_150","title":"醉清风","author":"SNH48"}]}},"error_code":22000,"module":[{"id":"25","style":1,"link_url":"","style_nums":"0","title_more":"更多","nums":4,"jump":"0"},{"id":"27","style":3,"link_url":"","style_nums":"0","pos":3,"title":"今日热点","key":"mix_2","picurl":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_150599170908d757527df896d28f62e60b3ad4f277.jpg","title_more":"","nums":0,"jump":""},{"id":"26","style":15,"link_url":"","style_nums":"0","pos":4,"title":"精选歌单","key":"diy","picurl":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_146537068808d757527df896d28f62e60b3ad4f277.jpg","title_more":"更多","nums":0,"jump":"3"},{"id":"32","style":7,"pos":5,"title":"广告图片(大)","link_url":"","picurl":"","title_more":"","key":"ad_big","jump":""},{"id":"48","style":4,"link_url":"","style_nums":"0","pos":6,"title":"U榜","key":"mod_29","picurl":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_15480515589a0bcbfafdf65428799eb5eded309d09.jpg","title_more":"","nums":0,"jump":""},{"id":"30","style":9,"link_url":"","style_nums":"0","pos":7,"title":"新歌首发","key":"mix_1","picurl":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_1465370722dfa35e762ce6c854d656eac195f7fcd5.jpg","title_more":"更多","nums":0,"jump":"7"},{"id":"29","style":10,"link_url":"","style_nums":"0","pos":8,"title":"今日推荐歌曲","key":"recsong","picurl":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_14653709721f134fb220693dff72cce9f8bae7d1ce.jpg","title_more":"更多","nums":0,"jump":"0"},{"id":"39","style":8,"link_url":"","style_nums":"0","pos":9,"title":"付费专辑","key":"mix_22","picurl":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_146640780395497fb5d1cc59a4cb81580819fbc07b.jpg","title_more":"","nums":0,"jump":"24"},{"id":"37","style":2,"pos":10,"title":"场景电台","link_url":"","picurl":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_1465370748a3defe2c3bdfa60416d432ea01210b30.jpg","title_more":"更多","key":"scene","jump":"6"},{"id":"21","style":14,"link_url":"","style_nums":"0","pos":11,"title":"乐播节目","key":"radio","picurl":"http:\/\/business.cdn.qianqian.com\/qianqian\/pic\/bos_client_146537101814c89604b4945401f5fc52b9fa2d6ea6.jpg","title_more":"更多","nums":0,"jump":"5"},{"id":"31","style":6,"pos":12,"title":"广告图片(小)","link_url":"","picurl":"","title_more":"","key":"ad_small","jump":""}]}

}
