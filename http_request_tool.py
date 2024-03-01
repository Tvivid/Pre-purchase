import requests
from concurrent.futures import ThreadPoolExecutor

def send_http_request(url, user_data):
    try:
        response = requests.post(url, json=user_data)
        print(f"Request for user {user_data['username']} completed with status code {response.status_code}")
    except Exception as e:
        print(f"Error sending request for user {user_data['username']}: {e}")

def create_user_data(user_id):
    # 유저 정보 생성 로직
    return {
        "username": f"user{user_id}",
        "email": f"user{user_id}@example.com",
        "password": "password123"
    }

def main():
    num_users = 3
    target_url = "http://localhost:8083/v1/signup" # 유저 생성 API 엔드포인트

    with ThreadPoolExecutor(max_workers=10) as executor:
        # 각 유저마다 고유한 정보를 가진 요청을 생성
        tasks = [executor.submit(send_http_request, target_url, create_user_data(i)) for i in range(1, num_users + 1)]

        # 모든 요청이 완료될 때까지 기다림
        for future in tasks:ls
            future.result()

if __name__ == "__main__":
    main()
