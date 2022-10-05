package lotto1;

public class Lotto1Main {

    // 정수 배열을 사용
    static int[] lottoNumber = new int[6];

    public static void main(String[] args) {
        // 아래의 순서로 기능 배치
        // 로또 번호 배열에 넣기 createLottoNumber
        // 버블 정렬 sortLottoNumber
        // 결과 출력 printLottoNumber
    }

    // 실제 구현 메서드 내용 채우기
    private static void printLottoNumber() {
        for(int i = 0; i < lottoNumber.length; i++) {
        	System.out.print(lottoNumber[i] + " ");
        }
    }

    private static void sortLottoNumber() {
        for(int i = 0; i < lottoNumber.length; i++) {
            for(int j=i+1;j<lottoNumber.length; j++){
                if(lottoNumber[i] > lottoNumber[j]) {
                    swap(i, j);
                }
            }
        }
    }

    private static void createLottoNumber() {
        for(int i = 0; i < 6; i++ ) {
            lottoNumber[i] = (int)(Math.random()*45);
             
            for(int j = 0; j < i; j++) {
                if(lottoNumber[j] == lottoNumber[i]) {
                    --i;
                    break;
                }
            }
            }
    }

    private static void swap(int a, int b) {
        int temp = lottoNumber[a];
        lottoNumber[a] = lottoNumber[b];
        lottoNumber[b] = temp;
    }
}
