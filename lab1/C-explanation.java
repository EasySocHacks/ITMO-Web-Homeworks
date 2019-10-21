
public class Main {
	private static final String JSESSIONID = ""; //Insert your Jsession ID
	private static final String BASE_TEXT = "curl 'http://1d3p.wp.codeforces.com/new' "
			+ "-H 'Connection: keep-alive' "
			+ "-H 'Pragma: no-cache' "
			+ "-H 'Cache-Control: no-cache' "
			+ "-H 'Origin: http://1d3p.wp.codeforces.com' "
			+ "-H 'Upgrade-Insecure-Requests: 1' "
			+ "-H 'Content-Type: application/x-www-form-urlencoded' "
			+ "-H 'User-Agent: Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36' "
			+ "-H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3' "
			+ "-H 'Referer: http://1d3p.wp.codeforces.com/' "
			+ "-H 'Accept-Encoding: gzip, deflate' "
			+ "-H 'Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7' "
			+ "-H 'Cookie: JSESSIONID="+ JSESSIONID +"' "
			+ "--compressed "
			+ "--insecure ";
	
	public static void main(String args[]) {
		for (int i = 1; i <= 100; i++) {
			System.out.format(BASE_TEXT + "--data '_af=34be50b38beccce4&proof=%d&amount=%d&submit=Submit'\n", i*i, i);
		}
	}
}
