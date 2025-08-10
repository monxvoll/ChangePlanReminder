# NetflixPlanReminder

A simple Java Telegram bot that reminds you to switch your Netflix subscription plan before the billing date.

## How it works

Netflix lets you change your plan anytime. This bot helps you save money by reminding you to upgrade to the most expensive plan, then downgrade to the cheapest one just before the monthly charge.

## Setup

1. Clone this repository.

2. Create a `.env` file in the root directory with the following variables:

- BOT_TOKEN=your_telegram_bot_token
- CHAT_ID=your_telegram_chat_id
- PAYMENT_DAY=your_netflix_billing_day

# Notes

- The bot sends a Telegram message reminder one day before your billing date at 10 AM.

- Make sure your system or deployment environment has these environment variables set.

- To keep the bot running 24/7, consider deploying it on a cloud service like Render.
