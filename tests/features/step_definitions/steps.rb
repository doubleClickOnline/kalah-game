require 'faraday'
require 'rspec'
require 'json-diff'

Given('Game is created') do
  @resp = Faraday.post("http://#{ENV['HOST']}:#{ENV['PORT']}/games")
  expect(JSON.parse(@resp.body)).not_to be_empty
end

Then('I should receive game') do
  expect(@resp.status).to eq(201)
  expect(@resp.headers['content-type']).to eq('application/json')
  id = JSON.parse(@resp.body)['id']
  expect(JSON.parse(@resp.body)['url']).to eq("http://#{ENV['HOST']}:#{ENV['PORT']}/games/#{id}")
end

When('I make a move') do
  id = JSON.parse(@resp.body)['id']
  @resp = Faraday.put("http://#{ENV['HOST']}:#{ENV['PORT']}/games/#{id}/pits/1")
  resp_id = JSON.parse(@resp.body)['id']
  expect(resp_id).to eq(id)
  expect(@resp.status).to eq(200)
  expect(@resp.headers['content-type']).to eq('application/json')
end

Then('Game is updated') do
  expectedStatus = {"1"=>"0","2"=>"7","3"=>"7","4"=>"7","5"=>"7","6"=>"7","7"=>"1","8"=>"6","9"=>"6","10"=>"6","11"=>"6","12"=>"6","13"=>"6","14"=>"0"}
  status = JSON.parse(@resp.body)['status']
  diff = JsonDiff.diff(status, expectedStatus)
  expect(diff).to be_empty
end
